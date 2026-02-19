package com.suviny.wiple.global.common.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link CodeEnum} 구현체 전용 타입핸들러
 * <p>
 * {@link CodeEnum}의 구현체인 열거형 내에 정의된 {@link String} 타입의 코드값을 데이터베이스 컬럼과의 매핑을 통해 저장하거나,
 * 데이터베이스로부터 읽어들인 코드값을 열거형으로 변환하는 작업을 수행하는 타입 핸들러
 * 
 * @param <E> {@link CodeEnum}을 구현한 {@link Enum} 타입
 */

@MappedTypes(CodeEnum.class)
public class CodeEnumTypeHandler <E extends Enum<E> & CodeEnum> extends BaseTypeHandler<E> {

    private final Class<E> type;

    private final E[] constants;

    public CodeEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null!");
        }
        this.type = type;
        this.constants = type.getEnumConstants();
        if (!type.isInterface() && this.constants == null) {
            throw new IllegalArgumentException("'" + type.getSimpleName() + "' does not represent an enum type!");
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String code = rs.getString(columnName);
        return rs.wasNull() ? null : resolve(code);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String code = rs.getString(columnIndex);
        return rs.wasNull() ? null : resolve(code);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String code = cs.getString(columnIndex);
        return cs.wasNull() ? null : resolve(code);
    }

    private E resolve(String code) {
        for (E e : constants) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Cannot convert '" + code + "' to '" + type.getSimpleName() + "' by code value.");
    }
}