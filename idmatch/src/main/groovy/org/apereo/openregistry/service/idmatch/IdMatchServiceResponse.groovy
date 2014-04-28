package org.apereo.openregistry.service.idmatch

class IdMatchServiceResponse {
    Status status
    String referenceId
    def fullResponse

    enum Status {
        OK, NOT_FOUND, CREATED, MULTIPLE_CHOICES, ACCEPTED

        static getStatusFromInt(int i) {
            switch (i) {
                case 200:
                    return OK
                case 404:
                    return NOT_FOUND
                case 201:
                    return CREATED
                case 300:
                    return MULTIPLE_CHOICES
                case 202:
                    return ACCEPTED
                default:
                    return null
            }
        }
    }
}
