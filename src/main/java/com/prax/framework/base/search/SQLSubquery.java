package com.prax.framework.base.search;

import java.io.Serializable;
import java.util.Vector;

/**
 * @author Alvin.Hu
 *
 */
public class SQLSubquery implements Serializable {
    private static final long serialVersionUID = 300100L;
    public final static int WC_NONE = 0;
    public final static int WC_LEFT = 1;
    public final static int WC_RIGHT = 2;
    public final static int WC_BOTH = 3;
    
    SQLSelectClause selectClause = new SQLSelectClause();
    SQLFromClause fromClause = new SQLFromClause();
    SQLWhereClause whereClause = new SQLWhereClause();
    SQLGroupClause groupClause = new SQLGroupClause();
    SQLHavingClause havingClause = new SQLHavingClause();
    SQLOrderClause orderClause = new SQLOrderClause();
    int pageSize = 15;
    int pageNumber = 1;
    
    public int getPageNumber() {
        return pageNumber;
    }
    
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public SQLSubquery() {
        super();
    }
    
    public SQLFromClause getFromClause() {
        return fromClause;
    }
    
    public SQLGroupClause getGroupClause() {
        return groupClause;
    }
    
    public SQLHavingClause getHavingClause() {
        return havingClause;
    }
    
    public SQLOrderClause getOrderClause() {
        return orderClause;
    }
    
    public SQLSelectClause getSelectClause() {
        return selectClause;
    }
    
    public SQLWhereClause getWhereClause() {
        return whereClause;
    }
    
    public void addSelect(String name) {
        getSelectClause().add(name);
    }
    
    public void addSelect(String name, String alias) {
        getSelectClause().add(name, alias);
    }
    
    public void clearSelect() {
        getSelectClause().clear();
    }
    
    public void addFrom(String name) {
        getFromClause().add(name);
    }
    
    public void addFrom(String name, String alias) {
        getFromClause().add(name, alias);
    }
    
    public void addWhere(String condition) {
        getWhereClause().add(condition);
    }
    
    public void clearWhere(String condition) {
        getWhereClause().clear(condition);
    }
    
    public void addWhere(String condition, Object parameterValue) {
        getWhereClause().add(condition, parameterValue);
    }
    
    public void addWhere(String condition, Object parameterValue, int wildcard) {
        getWhereClause().add(condition, parameterValue, wildcard);
    }
    
    public void addWhere(String condition, Object parameterValue, int wildcard, boolean toUpperCase) {
        if (toUpperCase && parameterValue != null && parameterValue instanceof String)
            parameterValue = ((String) parameterValue).toUpperCase();
        getWhereClause().add(condition, parameterValue, wildcard);
    }
    
    public void addGroup(String name) {
        getGroupClause().add(name);
    }
    
    public void addHaving(String condition) {
        getHavingClause().add(condition);
    }
    
    public void addHaving(String condition, Object parameterValue) {
        getHavingClause().add(condition, parameterValue);
    }
    
    public void addHaving(String condition, Object parameterValue, int wildcard) {
        getHavingClause().add(condition, parameterValue, wildcard);
    }
    
    public void addOrder(String name) {
        addOrder(name, null);
    }
    
    public void addOrder(String name, String order) {
        getOrderClause().add(name, order);
    }
    
    public String getCountStatement() {
        StringBuffer sb = new StringBuffer();
        if (groupClause.toString().trim().length() == 0
                && selectClause.toString().toLowerCase().indexOf(" distinct ") == -1
                && selectClause.toString().toLowerCase().indexOf(" count(") == -1
                && selectClause.toString().toLowerCase().indexOf(" sum(") == -1
                && selectClause.toString().toLowerCase().indexOf(" avg(") == -1
                && selectClause.toString().toLowerCase().indexOf(" min(") == -1
                && selectClause.toString().toLowerCase().indexOf(" max(") == -1) {
            SQLSelectClause s = new SQLSelectClause();
            s.add("count(*)", null);
            sb.append(s.toString());
            sb.append(fromClause.toString());
            sb.append(whereClause.toString());
        } else {
            sb.append("select count(*) from (");
            sb.append(selectClause.toString());
            sb.append(fromClause.toString());
            sb.append(whereClause.toString());
            sb.append(groupClause.toString());
            sb.append(havingClause.toString());
            sb.append(") o");
        }
        return sb.toString();
    }
    
    public String getSqlStatement() {
        StringBuffer sb = new StringBuffer();
        sb.append(selectClause.toString());
        sb.append(fromClause.toString());
        sb.append(whereClause.toString());
        sb.append(groupClause.toString());
        sb.append(havingClause.toString());
        sb.append(orderClause.toString());
        return sb.toString();
    }
    
    public Vector getParametersVector() {
        Vector v = whereClause.getParametersVector();
        v.addAll(havingClause.getParametersVector());
        return v;
    }
    
    public String toString() {
        return getSqlStatement() + getParametersVector().toString();
    }
    
    public class SQLSelectClause implements Serializable {
        private static final long serialVersionUID = 300100L;
        private Vector items;
        
        public SQLSelectClause() {
            items = new Vector();
        }
        
        public String toString() {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < items.size(); ++i) {
                SQLSelectItem item = (SQLSelectItem) items.get(i);
                if (i == 0)
                    sb.append("select " + item.toString());
                else
                    sb.append(", " + item.toString());
            }
            return sb.toString();
        }
        
        public void add(String name) {
            add(name, null);
        }
        
        public void add(String name, String alias) {
            items.add(new SQLSelectItem(name, alias));
        }
        
        public void clear() {
            items.clear();
        }
    }
    
    public class SQLSelectItem implements Serializable {
        private static final long serialVersionUID = 300100L;
        public String name;
        public String alias;
        
        public SQLSelectItem(String name, String alias) {
            this.name = name;
            this.alias = alias;
        }
        
        public String toString() {
            if (alias != null) {
                return name + " " + alias;
            } else
                return name;
        }
    }
    
    public class SQLFromClause implements Serializable {
        private static final long serialVersionUID = 300100L;
        private Vector items;
        
        public SQLFromClause() {
            items = new Vector();
        }
        
        public Vector getItems() {
            return items;
        }
        
        public String toString() {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < items.size(); ++i) {
                SQLFromItem item = (SQLFromItem) items.get(i);
                if (i == 0)
                    sb.append(" from " + item.toString());
                else
                    sb.append(", " + item.toString());
            }
            return sb.toString();
        }
        
        public void add(String name) {
            add(name, null);
        }
        
        public void add(String name, String alias) {
            items.add(new SQLFromItem(name, alias));
        }
    }
    
    public class SQLFromItem extends SQLSelectItem {
        private static final long serialVersionUID = 300100L;
        
        public SQLFromItem(String name, String alias) {
            super(name, alias);
        }
    }
    
    public class SQLWhereClause implements Serializable {
        private static final long serialVersionUID = 300100L;
        private Vector<SQLWhereItem> items;
        
        public SQLWhereClause() {
            items = new Vector<SQLWhereItem>();
        }
        
        public Vector<SQLWhereItem> getItems() {
            return items;
        }
        
        public void add(String condition) {
            add(condition, null, WC_NONE, false);
        }
        
        public void clear(String codition) {
            SQLWhereItem item = new SQLWhereItem(codition, null, WC_NONE, false);
            for (int i = items.size() - 1; i >= 0; --i)
                if (item.equals((SQLWhereItem) items.get(i))) {
                    items.remove(i);
                    break;
                }
        }
        
        public void add(String condition, Object parameterValue) {
            add(condition, parameterValue, WC_NONE, true);
        }
        
        public void add(String condition, Object parameterValue, int wildcard) {
            add(condition, parameterValue, wildcard, true);
        }
        
        public void add(String condition, Object parameterValue, int wildcard, boolean hasParameter) {
            items.add(new SQLWhereItem(condition, parameterValue, wildcard, hasParameter));
        }
        
        public String toString() {
            String s = toStringWithoutWhere();
            if (s == null || "".equals(s.trim()))
                return "";
            return " where ".concat(toStringWithoutWhere());
        }
        
        public String toStringWithoutWhere() {
            StringBuffer sb = new StringBuffer();
            
            if (items.size() > 0) {
                
                for (int i = 0; i < items.size(); ++i) {
                    SQLWhereItem item = (SQLWhereItem) items.get(i);
                    if (!item.hasParameter || item.parameterValue != null) {
                        // 如果算符为in, 要构造多个参数
                        String s = item.condition.toLowerCase();
                        if (item.condition.indexOf(" in ") >= 0) {
                            Object[] param = (Object[]) item.parameterValue;
                            if (param != null) {
                                s = "";
                                
                                for (int j = 0; j < param.length; ++j) {
                                    if (j > 0)
                                        s = s + ",";
                                    s = s + "?";
                                }
                                s = " (" + s + ")";
                                sb.append(" " + item.condition.replaceAll("\\?", s));
                                
                            } else
                                sb.append(" " + item.condition);
                        } else
                            sb.append(" " + item.condition);
                    }
                }
            }
            
            return sb.toString();
        }
        
        public Vector getParametersVector() {
            Vector v = new Vector(items.size());
            
            for (int i = 0; i < items.size(); ++i) {
                SQLWhereItem item = (SQLWhereItem) items.get(i);
                if (item.parameterValue != null) {
                    if (item.parameterValue instanceof Object[]) {
                        Object[] param = (Object[]) item.parameterValue;
                        for (int j = 0; j < param.length; ++j) {
                            addSingleObjectToVector(v, param[j], item.wildcard);
                        }
                    } else {
                        addSingleObjectToVector(v, item.parameterValue, item.wildcard);
                    }
                }
            }
            
            return v;
        }
        
        private void addSingleObjectToVector(Vector v, Object param, int wildcard) {
            if (param instanceof String) {
                if (wildcard == WC_NONE)
                    v.add(param);
                else if (wildcard == WC_LEFT)
                    v.add("%" + param);
                else if (wildcard == WC_RIGHT)
                    v.add(param + "%");
                else if (wildcard == WC_BOTH)
                    v.add("%" + param + "%");
            } else if (param instanceof java.util.Date) {
                // be sure to convert a java.util.Date to a java.sql.Timestamp!
                java.util.Date d = (java.util.Date) param;
                v.add(new java.sql.Timestamp(d.getTime()));
            } else {
                v.add(param);
            }
        }
    }
    
    public class SQLWhereItem implements Serializable {
        private static final long serialVersionUID = 300100L;
        protected String condition;
        protected Object parameterValue;
        protected int wildcard;
        protected boolean hasParameter;
        
        public SQLWhereItem(String condition, Object parameterValue, int wildcard, boolean hasParameter) {
            this.condition = condition;
            if (!(parameterValue instanceof String) || ((String) parameterValue).trim().length() > 0)
                this.parameterValue = parameterValue;
            else
                this.parameterValue = null;
            this.wildcard = wildcard;
            this.hasParameter = hasParameter;
        }
        
        public boolean equals(SQLWhereItem item) {
            boolean r = condition.equals(item.condition);
            if (parameterValue == null)
                r = r && (item.parameterValue == null);
            else
                r = r && parameterValue.equals(item.parameterValue);
            r = r && wildcard == item.wildcard;
            r = r && hasParameter == item.hasParameter;
            return r;
        }
    }
    
    public class SQLGroupClause implements Serializable {
        private static final long serialVersionUID = 300100L;
        private Vector items;
        
        public SQLGroupClause() {
            items = new Vector();
        }
        
        public void add(String name) {
            items.add(new SQLGroupItem(name));
        }
        
        public String toString() {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < items.size(); ++i) {
                SQLGroupItem item = (SQLGroupItem) items.get(i);
                if (i == 0)
                    sb.append(" group by " + item.toString());
                else
                    sb.append(", " + item.toString());
            }
            return sb.toString();
        }
    }
    
    public class SQLGroupItem implements Serializable {
        private static final long serialVersionUID = 300100L;
        private String name;
        
        public SQLGroupItem(String name) {
            this.name = name;
        }
        
        public String toString() {
            return name;
        }
    }
    
    public class SQLHavingClause implements Serializable {
        private static final long serialVersionUID = 300100L;
        private Vector items;
        
        public SQLHavingClause() {
            items = new Vector();
        }
        
        public Vector getItems() {
            return items;
        }
        
        public void add(String condition) {
            add(condition, null, WC_NONE, false);
        }
        
        public void add(String condition, Object parameterValue) {
            add(condition, parameterValue, WC_NONE, true);
        }
        
        public void add(String condition, Object parameterValue, int wildcard) {
            add(condition, parameterValue, wildcard, true);
        }
        
        public void add(String condition, Object parameterValue, int wildcard, boolean hasParameter) {
            items.add(new SQLHavingItem(condition, parameterValue, wildcard, hasParameter));
        }
        
        public String toString() {
            StringBuffer sb = new StringBuffer();
            
            if (items.size() > 0) {
                sb.append(" having");
                for (int i = 0; i < items.size(); ++i) {
                    SQLHavingItem item = (SQLHavingItem) items.get(i);
                    if (!item.hasParameter || item.parameterValue != null)
                        sb.append(" " + item.toString());
                }
            }
            
            return sb.toString();
        }
        
        public Vector getParametersVector() {
            Vector v = new Vector(items.size());
            for (int i = 0; i < items.size(); ++i) {
                SQLWhereItem item = (SQLWhereItem) items.get(i);
                if (item.parameterValue != null) {
                    if (item.parameterValue instanceof Object[]) {
                        Object[] param = (Object[]) item.parameterValue;
                        for (int j = 0; j < param.length; ++j) {
                            addSingleObjectToVector(v, param[j], item.wildcard);
                        }
                    } else {
                        addSingleObjectToVector(v, item.parameterValue, item.wildcard);
                    }
                }
            }
            
            return v;
        }
        
        private void addSingleObjectToVector(Vector v, Object param, int wildcard) {
            if (param instanceof String) {
                if (wildcard == WC_NONE)
                    v.add(param);
                else if (wildcard == WC_LEFT)
                    v.add("%" + param);
                else if (wildcard == WC_RIGHT)
                    v.add(param + "%");
                else if (wildcard == WC_BOTH)
                    v.add("%" + param + "%");
            } else if (param instanceof java.util.Date) {
                // be sure to convert a java.util.Date to a java.sql.Timestamp!
                java.util.Date d = (java.util.Date) param;
                v.add(new java.sql.Timestamp(d.getTime()));
            } else {
                v.add(param);
            }
        }
        
    }
    
    public class SQLHavingItem extends SQLWhereItem {
        private static final long serialVersionUID = 300100L;
        
        public SQLHavingItem(String condition, Object parameterStringValue, int wildcard, boolean hasParameter) {
            super(condition, parameterStringValue, wildcard, hasParameter);
        }
    }
    
    public class SQLOrderClause implements Serializable {
        private static final long serialVersionUID = 300100L;
        private Vector items;
        
        public SQLOrderClause() {
            items = new Vector();
        }
        
        public Vector getItems() {
            return items;
        }
        
        public void setItems(Vector items) {
            this.items = items;
        }
        
        public String toString() {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < items.size(); ++i) {
                SQLOrderItem item = (SQLOrderItem) items.get(i);
                if (i == 0)
                    sb.append(" order by " + item.toString());
                else
                    sb.append(", " + item.toString());
            }
            return sb.toString();
        }
        
        public void add(String name, String order) {
            items.add(new SQLOrderItem(name, order));
        }
    }
    
    public class SQLOrderItem extends SQLSelectItem {
        private static final long serialVersionUID = 300100L;
        
        public SQLOrderItem(String name, String order) {
            super(name, order);
        }
    }
    
    public int getFirstResult() {
        int result = (getPageNumber() - 1) * getPageSize();
        if (result < 0)
            result = 0;
        return result;
    }
    
    public int getMaxResults() {
        return getPageSize();
    }
}
