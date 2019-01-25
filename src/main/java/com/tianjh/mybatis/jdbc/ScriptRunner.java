package com.tianjh.mybatis.jdbc;

import org.apache.ibatis.jdbc.RuntimeSqlException;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.*;

/**
 * @author: wb-tjh438466
 * @date: 2018/12/6
 * @since: 1 description:
 */
public class ScriptRunner {

    /**
     * 换行符
     */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator", "\n");
    /**
     * 数据库连接
     */
    private Connection connection;

    /**
     * 遇到错误的时候停止程序运行
     */
    private Boolean stopOnError;

    /**
     * 是否全读取
     */
    private Boolean sendFullScript;
    /**
     * 是否自动提交，true是，false 否（手动提交，这样保证sql里面有错误的时候可以回滚）
     */
    private Boolean autoCommit;

    /**
     * 正常日志信息打印流
     */
    private PrintWriter logWriter;

    /**
     * 错误日志打印流
     */
    private PrintWriter errorLogWriter;

    /**
     * 是否转译处理，true是，false不启用转译处理
     */
    private boolean escapeProcessing = true;

    /**
     * 是否去除换行的回车符
     */
    private boolean removeCRs;

    /**
     * 语句分割符，确定sql到这里是结束
     */
    private String delimiter = DEFAULT_DELIMITER;
    /**
     * 默认的语句结尾符
     */
    private static final String DEFAULT_DELIMITER = ";";

    /**
     * 是否整行就只有分割符
     */
    private boolean fullLineDelimiter = false;

    public Boolean getSendFullScript() {
        return sendFullScript;
    }

    public void setSendFullScript(Boolean sendFullScript) {
        this.sendFullScript = sendFullScript;
    }

    public boolean isEscapeProcessing() {
        return escapeProcessing;
    }

    public void setEscapeProcessing(boolean escapeProcessing) {
        this.escapeProcessing = escapeProcessing;
    }

    public boolean isRemoveCRs() {
        return removeCRs;
    }

    public void setRemoveCRs(boolean removeCRs) {
        this.removeCRs = removeCRs;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public ScriptRunner(Connection connection) {
        this.connection=connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Boolean getStopOnError() {
        return stopOnError;
    }

    public void setStopOnError(Boolean stopOnError) {
        this.stopOnError = stopOnError;
    }

    public PrintWriter getLogWriter() {
        return logWriter;
    }

    public void setLogWriter(PrintWriter logWriter) {
        this.logWriter = logWriter;
    }

    public PrintWriter getErrorLogWriter() {
        return errorLogWriter;
    }

    public void setErrorLogWriter(PrintWriter errorLogWriter) {
        this.errorLogWriter = errorLogWriter;
    }

    public Boolean getAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(Boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public void runScript(Reader reader) {
        setAutoCommit();
        try {
            if (sendFullScript) {
                executeFullScript(reader);
            } else {
                executeLineByLine(reader);
            }
        } finally {
            rollbackConnection();
        }
    }

    /**
     * sql语句是换行符结束
     * @param reader
     */
    private void executeFullScript(Reader reader) {
        StringBuilder script = new StringBuilder();
        try {
            BufferedReader lineReader = new BufferedReader(reader);
            String line;
            while ((line = lineReader.readLine()) != null) {
                script.append(line);
                script.append(LINE_SEPARATOR);
            }
            String command = script.toString();
            println(command);
           executeStatement(command);
            commitConnection();
        } catch (Exception e) {
            String message = "Error executing: " + script + ".  Cause: " + e;
            printlnError(message);
            throw new RuntimeSqlException(message, e);
        }
    }

    /**
     * sql语句是由分隔符结束
     * @param reader
     */
    private void executeLineByLine(Reader reader) {
        StringBuilder command = new StringBuilder();
        try {
            BufferedReader lineReader = new BufferedReader(reader);
            String line;
            while ((line = lineReader.readLine()) != null) {
                command = handleLine(command, line);
            }
            commitConnection();
            checkForMissingLineTerminator(command);
        } catch (Exception e) {
            String message = "Error executing: " + command + ".  Cause: " + e;
            printlnError(message);
            throw new RuntimeSqlException(message, e);
        }
    }

    /**
     * 如果当前行没有分隔符结尾，直接报错
     * @param command
     */
    private void checkForMissingLineTerminator(StringBuilder command) {
        if (command != null && command.toString().trim().length() > 0) {
            throw new RuntimeSqlException("Line missing end-of-line terminator (" + delimiter + ") => " + command);
        }
    }
    private StringBuilder handleLine(StringBuilder command, String line) throws SQLException,
        UnsupportedEncodingException {
        String trimmedLine = line.trim();
        if (lineIsComment(trimmedLine)) {
            //根据注解设置sql语句结束符  设置格式：--//@DELIMITER /
            final String cleanedString = trimmedLine.substring(2).trim().replaceFirst("//", "");
            if(cleanedString.toUpperCase().startsWith("@DELIMITER")) {
                delimiter = cleanedString.substring(11,12);
                return command;
            }
            println(trimmedLine);
        } else if (commandReadyToExecute(trimmedLine)) {
            command.append(line.substring(0, line.lastIndexOf(delimiter)));
            command.append(LINE_SEPARATOR);
            println(command);
            executeStatement(command.toString());
            command.setLength(0);
        } else if (trimmedLine.length() > 0) {
            command.append(line);
            command.append(LINE_SEPARATOR);
        }
        return command;
    }

    private boolean commandReadyToExecute(String trimmedLine) {
        // issue #561 remove anything after the delimiter
        return !fullLineDelimiter && trimmedLine.contains(delimiter) || fullLineDelimiter && trimmedLine.equals(delimiter);
    }
    /**
     * 判断是否是注释语句
     * @param trimmedLine
     * @return
     */
    private boolean lineIsComment(String trimmedLine) {
        return trimmedLine.startsWith("//") || trimmedLine.startsWith("--");
    }
    /**
     * 手动提交对应的手动回滚
     */
    private void rollbackConnection() {
        try {
            if (!connection.getAutoCommit()) {
                connection.rollback();
            }
        } catch (Throwable t) {
            // ignore
        }
    }
    private void setAutoCommit() {
        try {
            connection.setAutoCommit(this.autoCommit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 如果不是自动提交，需要提交执行
     */
    private void commitConnection() {
        try {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        } catch (Throwable t) {
            throw new RuntimeSqlException("Could not commit transaction. Cause: " + t, t);
        }
    }
    /**
     * 打印信息到控制台
     * @param o
     */
    private void println(Object o) {
        if (logWriter != null) {
            logWriter.println(o);
            logWriter.flush();
        }
    }

    /**
     * 打印错误日志到控制台
     * @param o
     */
    private void printlnError(Object o) {
        if (errorLogWriter != null) {
            errorLogWriter.println(o);
            errorLogWriter.flush();
        }
    }


    /**
     * 执行sql语句
     * @param command
     * @throws SQLException
     */
    private void executeStatement(String command) throws SQLException {
        boolean hasResults = false;
        Statement statement = connection.createStatement();
        statement.setEscapeProcessing(escapeProcessing);
        String sql = command;
        if (removeCRs) {
            sql = sql.replaceAll("\r\n", "\n");
        }
        if (stopOnError) {
            hasResults = statement.execute(sql);
        } else {
            try {
                hasResults = statement.execute(sql);
            } catch (SQLException e) {
                String message = "Error executing: " + command + ".  Cause: " + e;
                printlnError(message);
            }
        }
        printResults(statement, hasResults);
        try {
            statement.close();
        } catch (Exception e) {
            // Ignore to workaround a bug in some connection pools
        }
    }

    /**
     * 打印执行结果
     * @param statement
     * @param hasResults
     */
    private void printResults(Statement statement, boolean hasResults) {
        try {
            if (hasResults) {
                ResultSet rs = statement.getResultSet();
                if (rs != null) {
                    ResultSetMetaData md = rs.getMetaData();
                    int cols = md.getColumnCount();
                    for (int i = 0; i < cols; i++) {
                        String name = md.getColumnLabel(i + 1);
                        print(name + "\t");
                    }
                    //换行
                    println("");
                    while (rs.next()) {
                        for (int i = 0; i < cols; i++) {
                            String value = rs.getString(i + 1);
                            print(value + "\t");
                        }
                        println("");
                    }
                }
            }
        } catch (SQLException e) {
            printlnError("Error printing results: " + e.getMessage());
        }
    }

    /**
     * 打印信息到控制台，不换行
     * @param o
     */
    private void print(Object o) {
        if (logWriter != null) {
            logWriter.print(o);
            logWriter.flush();
        }
    }
}
