package com.spark.member.handler;


import com.spark.member.common.Tall;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

@MappedTypes(Tall.class)
public class TallTypeHandler implements TypeHandler<Tall> {

    @Override
    public void setParameter(java.sql.PreparedStatement ps, int i, Tall parameter, org.apache.ibatis.type.JdbcType jdbcType) throws java.sql.SQLException {
        ps.setString(i, parameter.name()); // Enum 값을 DB에 저장할 때: enum의 이름을 문자열로 저장
    }

    @Override
    public Tall getResult(java.sql.ResultSet rs, String columnName) throws java.sql.SQLException {
        String code = rs.getString(columnName);
        if (code == null) return null;
        return Tall.valueOf(rs.getString(columnName)); // DB에서 컬럼 이름으로 조회된 문자열을 Tall enum으로 변환
    }

    @Override
    public Tall getResult(java.sql.ResultSet rs, int columnIndex) throws java.sql.SQLException {
        String code = rs.getString(columnIndex);
        if (code == null) return null;
        return Tall.valueOf(rs.getString(columnIndex)); // DB에서 컬럼 인덱스로 조회된 문자열을 Tall enum으로 변환
    }

    @Override
    public Tall getResult(java.sql.CallableStatement cs, int columnIndex) throws java.sql.SQLException {
        String code = cs.getString(columnIndex);
        if (code == null) return null;
        return Tall.valueOf(cs.getString(columnIndex)); // 저장 프로시저 결과에서 인덱스로 조회된 문자열을 Tall enum으로 변환
    }


}
