/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.id;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.factory.spi.StandardGenerator;
import org.hibernate.internal.CoreLogging;
import org.hibernate.internal.CoreMessageLogger;

/**
 * Generates {@code string} values using the SQL Server NEWID() function.
 *
 * @author Joseph Fifield
 *
 * @deprecated use {@link org.hibernate.id.uuid.UuidGenerator}
 */
@Deprecated(since = "6.0")
public class GUIDGenerator implements IdentifierGenerator, StandardGenerator {
	private static final CoreMessageLogger LOG = CoreLogging.messageLogger( GUIDGenerator.class );

	private static boolean WARNED;

	public GUIDGenerator() {
		if ( !WARNED ) {
			WARNED = true;
			LOG.deprecatedUuidGenerator( UUIDGenerator.class.getName(), UUIDGenerationStrategy.class.getName() );
		}
	}

	public Object generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
		final String sql = session.getJdbcServices().getJdbcEnvironment().getDialect().getSelectGUIDString();
		try {
			final PreparedStatement st = session.getJdbcCoordinator().getStatementPreparer().prepareStatement( sql );
			try {
				final ResultSet rs = session.getJdbcCoordinator().getResultSetReturn().extract( st );
				try {
					if ( !rs.next() ) {
						throw new HibernateException( "The database returned no GUID identity value" );
					}
					final String result = rs.getString( 1 );
					LOG.guidGenerated( result );
					return result;
				}
				finally {
					session.getJdbcCoordinator().getLogicalConnection().getResourceRegistry().release( rs, st );
				}
			}
			finally {
				session.getJdbcCoordinator().getLogicalConnection().getResourceRegistry().release( st );
				session.getJdbcCoordinator().afterStatementExecution();
			}
		}
		catch (SQLException sqle) {
			throw session.getJdbcServices().getSqlExceptionHelper().convert(
					sqle,
					"could not retrieve GUID",
					sql
			);
		}
	}
}
