package mysqljavacat.databaseobjects;

import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author strelok
 */
public class FuncObj {
    private  Icon icon = new ImageIcon(getClass().getResource("/mysqljavacat/resources/ledicons/cog.png"));
    private String name;
    private static final ArrayList<FuncObj> obj_list = new ArrayList<FuncObj>();
    public FuncObj(String name_t){
        name = name_t;
    }
    public static ArrayList<FuncObj> getFucList(){
        return obj_list;
    }
    public Icon getIcon(){
        return icon;
    }
    @Override
    public String toString(){
        return name;
    }
    static{
        String all_lex =
           "ADD\n"                 +
           "ALL\n"                 +
           "ALLOW REVERSE SCANS\n" +
           "ALTER\n"               +
           "ANALYZE\n"             +
           "AND\n"                 +
           "AS\n"                  +
           "ASC\n"                 +
           "AUTOMATIC\n"           +
           "BEGIN\n"		 +
           "BEFORE\n"              +
           "BETWEEN\n"             +
           "BIGINT\n"              +
           "BINARY\n"              +
           "BLOB\n"                +
           "BOTH\n"                +
           "BUFFERPOOL\n"		 +
           "BY\n"                  +
           "CACHE\n"		 +
           "CALL\n"                +
           "CASCADE\n"             +
           "CASE\n"                +
           "CHANGE\n"              +
           "CHAR\n"                +
           "CHARACTER\n"           +
           "CHECK\n"               +
           "COLLATE\n"             +
           "COLUMN\n"              +
           "COMMIT\n"		 +
           "CONDITION\n"           +
           "CONSTANT\n"		 +
           "CONSTRAINT\n"          +
           "CONTINUE\n"            +
           "CONVERT\n"             +
           "CREATE\n"              +
           "CROSS\n"               +
           "CURSOR\n"              +
           "DATE\n"		 +
           "DATABASE\n"            +
           "DATABASES\n"           +
           "DEC\n"                 +
           "DECIMAL\n"             +
           "DECODE\n"		 +
           "DECLARE\n"             +
           "DEFAULT\n"             +
           "DELAYED\n"             +
           "DELETE\n"              +
           "DESC\n"                +
           "DESCRIBE\n"            +
           "DETERMINISTIC\n"       +
           "DISTINCT\n"            +
           "DISTINCTROW\n"         +
           "DIV\n"                 +
           "DOUBLE\n"              +
           "DROP\n"                +
           "DUAL\n"                +
           "EACH\n"                +
           "ELSE\n"                +
           "ELSEIF\n"              +
           "ENCLOSED\n"            +
           "END\n"		 +
           "ESCAPED\n"             +
           "EXCEPTION\n" 		 +
           "EXISTS\n"              +
           "EXIT\n"                +
           "EXPLAIN\n"             +
           "FALSE\n"               +
           "FETCH\n"               +
           "FLOAT\n"               +
           "FLOAT4\n"              +
           "FLOAT8\n"              +
           "FOR\n"                 +
           "FORCE\n"               +
           "FOREIGN\n"             +
           "FROM\n"                +
           "FUNCTION\n"		 +
           "FULLTEXT\n"            +
           "GLOBAL TEMPORARY\n"	 +
           "GRANT\n"               +
           "GROUP\n"               +
           "HAVING\n"              +
           "IF\n"                  +
           "IGNORE\n"              +
           "IN\n"                  +
           "INDEX\n"               +
           "INFILE\n"              +
           "INNER\n"               +
           "INOUT\n"               +
           "INSENSITIVE\n"         +
           "INSERT\n"              +
           "INT\n"                 +
           "INTEGER\n"             +
           "INTERVAL\n"            +
           "INTO\n"                +
           "IS\n"                  +
           "IS REF CURSOR\n"	 +
           "ITERATE\n"             +
           "JOIN\n"                +
           "KEY\n"                 +
           "KEYS\n"                +
           "KILL\n"                +
           "LEADING\n"             +
           "LEAVE\n"               +
           "LEFT\n"                +
           "LIKE\n"                +
           "LIMIT\n"               +
           "LINES\n"               +
           "LOAD\n"                +
           "LOCK\n"                +
           "LONG\n"                +
           "LOOP\n"                +
           "MATCH\n"               +
           "MERGE\n"               +
           "MINVALUE\n"		 +
           "MAXVALUE\n"		 +
           "MOD\n"                 +
           "MODIFIES\n"            +
           "NATURAL\n"             +
           "NOCYCLE\n"		 +
           "NOORDER\n"		 +
           "NOT\n"                 +
           "NULL\n"                +
           "NUMERIC\n"             +
           "NUMBER\n"              +
           "ON\n"                  +
           "OPEN\n"		 +
           "OPTIMIZE\n"            +
           "OPTION\n"              +
           "OPTIONALLY\n"          +
           "OR\n"                  +
           "ORDER\n"               +
           "OTHERS\n"		 +
           "OUT\n"                 +
           "OUTER\n"               +
           "OUTFILE\n"             +
           "PACKAGE\n"		 +
           "PACKAGE BODY\n"	 +
           "PAGESIZE\n"		 +
           "PLS_INTEGER\n"	 +
           "PRAGMA\n"		 +
           "PRECISION\n"           +
           "PRIMARY\n"             +
           "PROCEDURE\n"           +
           "PURGE\n"               +
           "RAISE\n"		 +
           "READ\n"                +
           "READS\n"               +
           "REAL\n"                +
           "REFERENCES\n"          +
           "REGEXP\n"              +
           "RELEASE\n"             +
           "RENAME\n"              +
           "REPEAT\n"              +
           "REPLACE\n"             +
           "REQUIRE\n"             +
           "RESTRICT\n"            +
           "RETURN\n"              +
           "REVOKE\n"              +
           "RIGHT\n"               +
           "RLIKE\n"               +
           "ROLLBACK\n"		 +
           "ROWCOUNT\n"		 +
           "ROWTYPE\n"		 +
           "SIZE\n"		 +
           "SCHEMA\n"              +
           "SCHEMAS\n"             +
           "SELECT\n"              +
           "SENSITIVE\n"           +
           "SEPARATOR\n"           +
           "SEQUENCE\n"		 +
           "SET\n"                 +
           "SHOW\n"                +
           "SMALLINT\n"            +
           "SONAME\n"              +
           "SPATIAL\n"             +
           "SPECIFIC\n"            +
           "SQL\n"                 +
           "SQLEXCEPTION\n"        +
           "SQLSTATE\n"            +
           "SQLWARNING\n"          +
           "STARTING\n"            +
           "SYSDATE\n"		 +
           "TABLE\n"               +
           "TABLESPACE\n"		 +
           "TERMINATED\n"          +
           "THEN\n"                +
           "TO\n"                  +
           "TO_CHAR\n"		 +
           "TO_DATE\n"		 +
           "TRAILING\n"            +
           "TRIGGER\n"             +
           "TRUE\n"                +
           "TRUNCATE\n"            +
           "TYPE\n"		 +
           "UNDO\n"                +
           "UNION\n"               +
           "UNIQUE\n"              +
           "UNLOCK\n"              +
           "UNSIGNED\n"            +
           "UPDATE\n"              +
           "USAGE\n"               +
           "USE\n"                 +
           "USER\n"		 +
           "USING\n"               +
           "VALUES\n"              +
           "VARBINARY\n"           +
           "VARCHAR\n"             +
           "VARCHAR2\n"            +
           "VARCHARACTER\n"        +
           "VARYING\n"             +
           "WHEN\n"                +
           "WHERE\n"               +
           "WHILE\n"               +
           "WITH\n"                +
           "WRITE\n"               +
           "XOR\n"                 +
           "ZEROFILL";
           for(String s : all_lex.split("\n")){
                obj_list.add(new FuncObj(s));
           }
    }


}
