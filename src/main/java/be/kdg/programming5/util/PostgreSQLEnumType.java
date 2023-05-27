package be.kdg.programming5.util;

import be.kdg.programming5.model.Role;
import org.hibernate.HibernateException;
import org.hibernate.type.EnumType;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class PostgreSQLEnumType extends EnumType<Role> {

	public void nullSafeSet(
			PreparedStatement st,
			Object value,
			int index,
			SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, Types.OTHER);
		} else {
			st.setObject(
					index,
					value.toString(),
					Types.OTHER
			);
		}
	}
}
