package org.apereo.openregistry.service.reconciliation

import org.apereo.openregistry.model.SystemOfRecordPerson

/**
 *
 * Service that reconciles possible multiple canonical person matches for a given {@link org.apereo.openregistry.model.SystemOfRecordPerson}
 *
 * Presents reconciliation result in the form of {@link ReconciliationResult}
 */
public interface ReconciliationService {

    ReconciliationResult reconcile(SystemOfRecordPerson systemOfRecordPerson)

}