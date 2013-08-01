package org.rhc.server;

/**
 * Created with IntelliJ IDEA.
 * User: Jun
 * Date: 1/8/13
 * Time: 9:56 AM
 * To change this template use File | Settings | File Templates.
 */

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;

public class IntegerArrayType implements UserType {

    protected static final int  SQLTYPE = java.sql.Types.ARRAY;

    @Override
    public Object nullSafeGet(final ResultSet rs, final String[] names, final SessionImplementor sessionImplementor, final Object owner) throws HibernateException, SQLException {
        Array array = rs.getArray(names[0]);
        Integer[] javaArray = (Integer[]) array.getArray();
        return ArrayUtils.toPrimitive(javaArray);
    }

    @Override
    public void nullSafeSet(final PreparedStatement statement, final Object object, final int i, final SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        Connection connection = statement.getConnection();

        int[] castObject = (int[]) object;
        Integer[] integers = ArrayUtils.toObject(castObject);
        Array array = connection.createArrayOf("integer", integers);

        statement.setArray(i, array);
    }

    @Override
    public Object assemble(final Serializable cached, final Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object deepCopy(final Object o) throws HibernateException {
        return o == null ? null : ((int[]) o).clone();
    }

    @Override
    public Serializable disassemble(final Object o) throws HibernateException {
        return (Serializable) o;
    }

    @Override
    public boolean equals(final Object x, final Object y) throws HibernateException {
        return x == null ? y == null : x.equals(y);
    }

    @Override
    public int hashCode(final Object o) throws HibernateException {
        return o == null ? 0 : o.hashCode();
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
        return original;
    }

    @Override
    public Class<int[]> returnedClass() {
        return int[].class;
    }

    @Override
    public int[] sqlTypes() {
        return new int[] { SQLTYPE };
    }
}

