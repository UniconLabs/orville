package org.apereo.openregistry.service.idmatch

class IdMatchServiceResponse {
    Status status
    String referenceId
    def fullResponse

    enum Status {OK, NOT_FOUND, CREATED, MULTIPLE_CHOICES, ACCEPTED}
}
