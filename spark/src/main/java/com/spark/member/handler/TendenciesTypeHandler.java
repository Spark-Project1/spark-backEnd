package com.spark.member.handler;

import com.spark.member.common.Tendencies;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

@MappedTypes(Tendencies.class)
public class TendenciesTypeHandler implements TypeHandler<Tendencies> {
    @Override
    public void setParameter(java.sql.PreparedStatement ps, int i, Tendencies parameter, org.apache.ibatis.type.JdbcType jdbcType) throws java.sql.SQLException {
        ps.setString(i, parameter.name()); // Enum 값을 DB에 저장할 때: enum의 이름을 문자열로 저장
    }

    @Override
    public Tendencies getResult(java.sql.ResultSet rs, String columnName) throws java.sql.SQLException {
        String code = rs.getString(columnName);
        if (code == null) return null;
        return Tendencies.valueOf(rs.getString(columnName)); // DB에서 컬럼 이름으로 조회된 문자열을 Tendencies enum으로 변환
    }

    @Override
    public Tendencies getResult(java.sql.ResultSet rs, int columnIndex) throws java.sql.SQLException {
        String code = rs.getString(columnIndex);
        if (code == null) return null;
        return Tendencies.valueOf(rs.getString(columnIndex)); // DB에서 컬럼 인덱스로 조회된 문자열을 Tendencies enum으로 변환
    }

    @Override
    public Tendencies getResult(java.sql.CallableStatement cs, int columnIndex) throws java.sql.SQLException {
        String code = cs.getString(columnIndex);
        if (code == null) return null;
        return Tendencies.valueOf(cs.getString(columnIndex)); // 저장 프로시저 결과에서 인덱스로 조회된 문자열을 Tendencies enum으로 변환
    }
}
