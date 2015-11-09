package liquibase.actionlogic.core.mssql;

import liquibase.actionlogic.core.InsertOrUpdateLogic;
import liquibase.database.Database;
import liquibase.database.core.mssql.MSSQLDatabase;
import liquibase.statement.core.InsertOrUpdateStatement;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.Table;

public class InsertOrUpdateLogicMSSQL extends InsertOrUpdateLogic {
    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MSSQLDatabase.class;
    }

    @Override
    protected String getRecordCheck(InsertOrUpdateStatement insertOrUpdateStatement, Database database, String whereClause) {
        StringBuffer recordCheck = new StringBuffer();
        recordCheck.append("DECLARE @reccount integer\n");
        recordCheck.append("SELECT @reccount = count(*) FROM ");
        recordCheck.append(database.escapeObjectName(new ObjectReference(insertOrUpdateStatement.getCatalogName(), insertOrUpdateStatement.getSchemaName(), insertOrUpdateStatement.getTableName()), Table.class));
        recordCheck.append(" WHERE ");
        recordCheck.append(whereClause);
        recordCheck.append("\n");
        recordCheck.append("IF @reccount = 0\n");

        return recordCheck.toString();
    }

//    @Override
//    protected String getInsertStatement(InsertOrUpdateStatement insertOrUpdateStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
//        StringBuffer insertBlock = new StringBuffer();
//        insertBlock.append("BEGIN\n");
//        insertBlock.append(super.getInsertStatement(insertOrUpdateStatement, database, sqlGeneratorChain));
//        insertBlock.append("END\n");
//
//        return insertBlock.toString();
//    }

    @Override
    protected String getElse(Database database) {
        return "ELSE\n";
    }

//    @Override
//    protected String getUpdateStatement(InsertOrUpdateStatement insertOrUpdateStatement, Database database, String whereClause, SqlGeneratorChain sqlGeneratorChain) throws LiquibaseException {
//        StringBuffer updateBlock = new StringBuffer();
//        updateBlock.append("BEGIN\n");
//        updateBlock.append(super.getUpdateStatement(insertOrUpdateStatement, database, whereClause, sqlGeneratorChain));
//        updateBlock.append("END\n");
//        return updateBlock.toString();
//    }
//
//    @Override
//    public Sql[] generateSql(InsertOrUpdateStatement insertOrUpdateStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
//        return super.generateSql(insertOrUpdateStatement, database, sqlGeneratorChain);    //To change body of overridden methods use File | Settings | File Templates.
//    }
}