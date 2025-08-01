package com.spark.member.handler;


import com.spark.member.common.Smock;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(Smock.class)
public class SmockTypeHandler implements TypeHandler<Smock> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Smock parameter, org.apache.ibatis.type.JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name()); // Enum 값을 DB에 저장할 때: enum의 이름("Y", "N", "A")을 문자열로 저장
    }

    @Override
    // SELECT 쿼리에서 컬럼 이름으로 값 조회
    public Smock getResult(ResultSet rs, String columnName) throws SQLException {
        String code = rs.getString(columnName);
        if (code == null) return null;
        return Smock.valueOf(rs.getString(columnName)); // DB에서 컬럼 이름으로 조회된 문자열("Y" 등)을 Smock enum으로 변환
    }

    @Override
    // SELECT 쿼리에서 컬럼 인덱스로 값 조회
    public Smock getResult(ResultSet rs, int columnIndex) throws SQLException {
        String code = rs.getString(columnIndex);
        if (code == null) return null;
        return Smock.valueOf(rs.getString(columnIndex)); // DB에서 컬럼 인덱스로 조회된 문자열을 Smock enum으로 변환
    }

    @Override
    // 프로시저 호출에서 CallableStatement를 사용하여 값을 조회
    public Smock getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String code = cs.getString(columnIndex);
        if (code == null) return null;
        return Smock.valueOf(cs.getString(columnIndex)); // 저장 프로시저 결과에서 인덱스로 조회된 문자열을 Smock enum으로 변환
    }

}
