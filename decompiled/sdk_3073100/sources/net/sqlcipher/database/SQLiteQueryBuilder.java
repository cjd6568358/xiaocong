package net.sqlcipher.database;

import android.text.TextUtils;
import android.util.Log;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import net.sqlcipher.Cursor;
import net.sqlcipher.DatabaseUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SQLiteQueryBuilder {
    private static final String TAG = "SQLiteQueryBuilder";
    private static final Pattern sLimitPattern = Pattern.compile("\\s*\\d+\\s*(,\\s*\\d+\\s*)?");
    private boolean mStrictProjectionMap;
    private Map<String, String> mProjectionMap = null;
    private String mTables = "";
    private StringBuilder mWhereClause = null;
    private boolean mDistinct = false;
    private SQLiteDatabase$CursorFactory mFactory = null;

    public void setDistinct(boolean distinct) {
        this.mDistinct = distinct;
    }

    public String getTables() {
        return this.mTables;
    }

    public void setTables(String inTables) {
        this.mTables = inTables;
    }

    public void appendWhere(CharSequence inWhere) {
        if (this.mWhereClause == null) {
            this.mWhereClause = new StringBuilder(inWhere.length() + 16);
        }
        if (this.mWhereClause.length() == 0) {
            this.mWhereClause.append('(');
        }
        this.mWhereClause.append(inWhere);
    }

    public void appendWhereEscapeString(String inWhere) {
        if (this.mWhereClause == null) {
            this.mWhereClause = new StringBuilder(inWhere.length() + 16);
        }
        if (this.mWhereClause.length() == 0) {
            this.mWhereClause.append('(');
        }
        DatabaseUtils.appendEscapedSQLString(this.mWhereClause, inWhere);
    }

    public void setProjectionMap(Map<String, String> columnMap) {
        this.mProjectionMap = columnMap;
    }

    public void setCursorFactory(SQLiteDatabase$CursorFactory factory) {
        this.mFactory = factory;
    }

    public void setStrictProjectionMap(boolean flag) {
        this.mStrictProjectionMap = flag;
    }

    public static String buildQueryString(boolean distinct, String tables, String[] columns, String where, String groupBy, String having, String orderBy, String limit) {
        if (TextUtils.isEmpty(groupBy) && !TextUtils.isEmpty(having)) {
            throw new IllegalArgumentException("HAVING clauses are only permitted when using a groupBy clause");
        }
        if (!TextUtils.isEmpty(limit) && !sLimitPattern.matcher(limit).matches()) {
            throw new IllegalArgumentException("invalid LIMIT clauses:" + limit);
        }
        StringBuilder query = new StringBuilder(120);
        query.append("SELECT ");
        if (distinct) {
            query.append("DISTINCT ");
        }
        if (columns != null && columns.length != 0) {
            appendColumns(query, columns);
        } else {
            query.append("* ");
        }
        query.append("FROM ");
        query.append(tables);
        appendClause(query, " WHERE ", where);
        appendClause(query, " GROUP BY ", groupBy);
        appendClause(query, " HAVING ", having);
        appendClause(query, " ORDER BY ", orderBy);
        appendClause(query, " LIMIT ", limit);
        return query.toString();
    }

    private static void appendClause(StringBuilder s, String name, String clause) {
        if (!TextUtils.isEmpty(clause)) {
            s.append(name);
            s.append(clause);
        }
    }

    private static void appendClauseEscapeClause(StringBuilder s, String name, String clause) {
        if (!TextUtils.isEmpty(clause)) {
            s.append(name);
            DatabaseUtils.appendEscapedSQLString(s, clause);
        }
    }

    public static void appendColumns(StringBuilder s, String[] columns) {
        int n = columns.length;
        for (int i = 0; i < n; i++) {
            String column = columns[i];
            if (column != null) {
                if (i > 0) {
                    s.append(", ");
                }
                s.append(column);
            }
        }
        s.append(' ');
    }

    public Cursor query(SQLiteDatabase db, String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder) {
        return query(db, projectionIn, selection, selectionArgs, groupBy, having, sortOrder, null);
    }

    public Cursor query(SQLiteDatabase db, String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder, String limit) {
        if (this.mTables == null) {
            return null;
        }
        String sql = buildQuery(projectionIn, selection, selectionArgs, groupBy, having, sortOrder, limit);
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "Performing query: " + sql);
        }
        return db.rawQueryWithFactory(this.mFactory, sql, selectionArgs, SQLiteDatabase.findEditTable(this.mTables));
    }

    public String buildQuery(String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder, String limit) {
        String[] projection = computeProjection(projectionIn);
        StringBuilder where = new StringBuilder();
        boolean hasBaseWhereClause = this.mWhereClause != null && this.mWhereClause.length() > 0;
        if (hasBaseWhereClause) {
            where.append(this.mWhereClause.toString());
            where.append(')');
        }
        if (selection != null && selection.length() > 0) {
            if (hasBaseWhereClause) {
                where.append(" AND ");
            }
            where.append('(');
            where.append(selection);
            where.append(')');
        }
        return buildQueryString(this.mDistinct, this.mTables, projection, where.toString(), groupBy, having, sortOrder, limit);
    }

    public String buildUnionSubQuery(String typeDiscriminatorColumn, String[] unionColumns, Set<String> columnsPresentInTable, int computedColumnsOffset, String typeDiscriminatorValue, String selection, String[] selectionArgs, String groupBy, String having) {
        int unionColumnsCount = unionColumns.length;
        String[] projectionIn = new String[unionColumnsCount];
        for (int i = 0; i < unionColumnsCount; i++) {
            String unionColumn = unionColumns[i];
            if (unionColumn.equals(typeDiscriminatorColumn)) {
                projectionIn[i] = "'" + typeDiscriminatorValue + "' AS " + typeDiscriminatorColumn;
            } else if (i <= computedColumnsOffset || columnsPresentInTable.contains(unionColumn)) {
                projectionIn[i] = unionColumn;
            } else {
                projectionIn[i] = "NULL AS " + unionColumn;
            }
        }
        return buildQuery(projectionIn, selection, selectionArgs, groupBy, having, null, null);
    }

    public String buildUnionQuery(String[] subQueries, String sortOrder, String limit) {
        StringBuilder query = new StringBuilder(128);
        int subQueryCount = subQueries.length;
        String unionOperator = this.mDistinct ? " UNION " : " UNION ALL ";
        for (int i = 0; i < subQueryCount; i++) {
            if (i > 0) {
                query.append(unionOperator);
            }
            query.append(subQueries[i]);
        }
        appendClause(query, " ORDER BY ", sortOrder);
        appendClause(query, " LIMIT ", limit);
        return query.toString();
    }

    private String[] computeProjection(String[] projectionIn) {
        if (projectionIn != null && projectionIn.length > 0) {
            if (this.mProjectionMap == null) {
                return projectionIn;
            }
            String[] projection = new String[projectionIn.length];
            int length = projectionIn.length;
            for (int i = 0; i < length; i++) {
                String userColumn = projectionIn[i];
                String column = this.mProjectionMap.get(userColumn);
                if (column != null) {
                    projection[i] = column;
                } else if (!this.mStrictProjectionMap && (userColumn.contains(" AS ") || userColumn.contains(" as "))) {
                    projection[i] = userColumn;
                } else {
                    throw new IllegalArgumentException("Invalid column " + projectionIn[i]);
                }
            }
            return projection;
        }
        if (this.mProjectionMap != null) {
            Set<Map.Entry<String, String>> entrySet = this.mProjectionMap.entrySet();
            String[] projection2 = new String[entrySet.size()];
            int i2 = 0;
            for (Map.Entry<String, String> entry : entrySet) {
                if (!entry.getKey().equals("_count")) {
                    projection2[i2] = entry.getValue();
                    i2++;
                }
            }
            return projection2;
        }
        return null;
    }
}
