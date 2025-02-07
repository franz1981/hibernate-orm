/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate;

import java.util.Collection;

import org.hibernate.engine.spi.FilterDefinition;

/**
 * Allows control over an enabled filter at runtime. In particular, allows
 * {@linkplain #setParameter(String, Object) arguments} to be assigned to
 * parameters declared by the filter.
 * <p>
 * A filter may be defined using {@link org.hibernate.annotations.FilterDef}
 * and {@link org.hibernate.annotations.Filter}, and must be explicitly
 * enabled at runtime by calling {@link Session#enableFilter(String)}.
 *
 * @see org.hibernate.annotations.FilterDef
 * @see Session#enableFilter(String)
 * @see FilterDefinition
 *
 * @author Steve Ebersole
 */
public interface Filter {

	/**
	 * Get the name of this filter.
	 *
	 * @return This filter's name.
	 */
	String getName();

	/**
	 * Get the associated {@link FilterDefinition definition} of this
	 * named filter.
	 *
	 * @return The filter definition
	 *
	 * @deprecated There is no plan to remove this operation, but its use
	 *             should be avoided since {@link FilterDefinition} is an
	 *             SPI type, and so this operation is a layer-breaker.
	 */
	@Deprecated
	FilterDefinition getFilterDefinition();

	/**
	 * Set the named parameter's value for this filter.
	 *
	 * @param name The parameter's name.
	 * @param value The value to be applied.
	 * @return This FilterImpl instance (for method chaining).
	 */
	Filter setParameter(String name, Object value);

	/**
	 * Set the named parameter's value list for this filter.  Used
	 * in conjunction with IN-style filter criteria.
	 *
	 * @param name The parameter's name.
	 * @param values The values to be expanded into an SQL IN list.
	 * @return This FilterImpl instance (for method chaining).
	 */
	Filter setParameterList(String name, Collection<?> values);

	/**
	 * Set the named parameter's value list for this filter.  Used
	 * in conjunction with IN-style filter criteria.
	 *
	 * @param name The parameter's name.
	 * @param values The values to be expanded into an SQL IN list.
	 * @return This FilterImpl instance (for method chaining).
	 */
	Filter setParameterList(String name, Object[] values);

	/**
	 * Perform validation of the filter state.  This is used to verify
	 * the state of the filter after its enablement and before its use.
	 *
	 * @throws HibernateException If the state is not currently valid.
	 */
	void validate() throws HibernateException;
}
