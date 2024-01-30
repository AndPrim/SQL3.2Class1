package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private SQLHelper() {
    }

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"),
                "app", "pass");
    }

    @SneakyThrows
    public static DataHelper.VerificationCode getVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created  LIMIT 1";
        var conn = getConn();
        var code = QUERY_RUNNER.query(conn, codeSQL, new ScalarHandler<String>());
        return new DataHelper.VerificationCode(code);
    }

    @SneakyThrows
    public static void cleanDatabase() {
        QUERY_RUNNER.execute(getConn(), "DELETE FROM auth_codes");
        QUERY_RUNNER.execute(getConn(), "DELETE FROM card_transactions");
        QUERY_RUNNER.execute(getConn(), "DELETE FROM cards");
        QUERY_RUNNER.execute(getConn(), "DELETE FROM users");
    }

    @SneakyThrows
    public static void cleanAuthCodes() {
        QUERY_RUNNER.execute(getConn(), "DELETE FROM auth_codes");
    }
}
