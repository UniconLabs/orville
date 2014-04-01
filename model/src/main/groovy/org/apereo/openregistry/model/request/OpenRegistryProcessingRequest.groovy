package org.apereo.openregistry.model.request

import groovy.transform.ToString

/**
 *
 * Entity model encapsulating the data describing a processing request i.e. a <i>raw</i> inbound data sent via REST API, for example,
 * as well as metadata about this request i.e. <i>add</>, <i>update</i>, <i>delete</i>, etc.
 */
@ToString
class OpenRegistryProcessingRequest {

    /**
     * A raw request data body
     */
    Object body

    /**
     * A requestor's SOR
     */
    String sor

    /**
     * A requestor's SOR person ID
     */
    String sorPersonId

    /**
     * Request type
     */
    TYPE type

    static enum TYPE {
        add, update, delete
    }
}
