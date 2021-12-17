/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.envers.query.internal.impl;

import java.util.Map;

import org.hibernate.Incubating;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.internal.reader.AuditReaderImplementor;
import org.hibernate.envers.internal.tools.query.QueryBuilder;
import org.hibernate.envers.query.AuditAssociationQuery;
import org.hibernate.envers.query.criteria.AuditCriterion;

import jakarta.persistence.criteria.JoinType;

/**
 * An {@link AuditAssociationQuery} implementation for {@link RevisionsOfEntityQuery}.
 *
 * @author Chris Cranford
 */
@Incubating
public class RevisionsOfEntityAssociationQuery<Q extends AuditQueryImplementor> extends AbstractAuditAssociationQuery<Q> {

	public RevisionsOfEntityAssociationQuery(
			EnversService enversService,
			AuditReaderImplementor auditReader,
			Q parent,
			QueryBuilder queryBuilder,
			String propertyName,
			JoinType joinType,
			Map<String, String> aliasToEntityNameMap,
			Map<String, String> aliastoComponentPropertyNameMap,
			String ownerAlias,
			String userSuppliedAlias,
			AuditCriterion onClauseCriterion) {
		super(
				enversService,
				auditReader,
				parent,
				queryBuilder,
				propertyName,
				joinType,
				aliasToEntityNameMap,
				aliastoComponentPropertyNameMap,
				ownerAlias,
				userSuppliedAlias,
				onClauseCriterion
		);
	}

	@Override
	protected AbstractAuditAssociationQuery<AbstractAuditAssociationQuery<Q>> createAssociationQuery(
			String associationName,
			JoinType joinType,
			String alias,
			AuditCriterion onClauseCriterion) {
		return new RevisionsOfEntityAssociationQuery<>(
				enversService,
				auditReader,
				this,
				queryBuilder,
				associationName,
				joinType,
				aliasToEntityNameMap,
				aliasToComponentPropertyNameMap,
				this.alias,
				alias,
				onClauseCriterion
		);
	}

}
