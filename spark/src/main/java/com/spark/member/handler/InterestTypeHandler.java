package com.spark.member.handler;

import com.spark.member.common.Interest;
import com.spark.member.common.Smock;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(Interest.class)
public class InterestTypeHandler implements TypeHandler<Interest> {


    @Override
    public void setParameter(PreparedStatement ps, int i, Interest parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public Interest getResult(ResultSet rs, String columnName) throws SQLException {
        String code = rs.getString(columnName);
        if (code == null) return null;
        return Interest.valueOf(rs.getString(columnName));
    }

    @Override
    public Interest getResult(ResultSet rs, int columnIndex) throws SQLException {
        String code = rs.getString(columnIndex);
        if (code == null) return null;
        return Interest.valueOf(rs.getString(columnIndex));
    }

    @Override
    public Interest getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String code = cs.getString(columnIndex);
        if (code == null) return null;
        return Interest.valueOf(cs.getString(columnIndex));
    }
}
