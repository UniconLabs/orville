package org.apereo.openregistry.service.idmatch

import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.apache.http.HttpStatus
import org.apereo.openregistry.model.NameIdentifier
import org.apereo.openregistry.model.Person
import org.apereo.openregistry.model.TokenIdentifier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

@Slf4j
@Component
class IdMatchService {
    @Autowired
    IdMatchServiceConfig serviceConfig

    RESTClient restClient

    @PostConstruct
    void afterPropertiesSet() {
        assert serviceConfig
        restClient = new RESTClient(serviceConfig.baseUrl)
        if (serviceConfig.preempt) {
            // TODO: check the security of this
            restClient.headers['Authorization'] = "Basic ${"${serviceConfig.username}:${serviceConfig.password}".bytes.encodeBase64()}"
        } else {
            restClient.auth.basic serviceConfig.username, serviceConfig.password
        }
    }

    IdMatchServiceResponse getIdMatchServiceResponse(Person person, String sor) {
        //TODO: really implement this
        try {

            def resp = restClient.get(path: "${serviceConfig.version}/people/${sor.toLowerCase()}/${sorPerson.sorId}")
            return new IdMatchServiceResponse(status: IdMatchServiceResponse.Status.OK, referenceId: sorPerson.sorId, fullResponse: [:])
        } catch (HttpResponseException e) {
            switch (e.statusCode) {
                case HttpStatus.SC_NOT_FOUND:
                    return new IdMatchServiceResponse(status: IdMatchServiceResponse.Status.NOT_FOUND, fullResponse: e.response)
                    break
            }
        }
    }

    IdMatchServiceResponse putIdMatchServiceResponse(Person person, String sor, String sorId) {
        // TODO: really implement this
        try {
            def builder = new JsonBuilder()
            builder.sorAttributes {
                names person.nameIdentifiers.collect { NameIdentifier nameId -> ["type": nameId.type.value, "given": nameId.name.givenName, "family": nameId.name.familyName] }
                identifiers(person.tokenIdentifiers.collect {TokenIdentifier id -> ["type": id.type.value, "identifier": id.token]} + ["type": "sor", "identifier": sorId])
            }
            def path = "${serviceConfig.version}/people/${sor.toLowerCase()}/${sorId}"
            def resp = restClient.put(
                    path: path,
                    body: builder.toString(),
                    requestContentType: ContentType.JSON
            )
            return new IdMatchServiceResponse(status: IdMatchServiceResponse.Status.OK, referenceId: resp.data.referenceId, fullResponse: resp.data)
        } catch (def e) {
            log.error(e.message, e)
        }
    }
}
