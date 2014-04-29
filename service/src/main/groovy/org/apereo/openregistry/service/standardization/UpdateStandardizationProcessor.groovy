package org.apereo.openregistry.service.standardization

import groovy.transform.EqualsAndHashCode
import groovy.util.logging.Slf4j
import org.apereo.openregistry.model.EmailAddressIdentifier
import org.apereo.openregistry.model.Name
import org.apereo.openregistry.model.NameIdentifier
import org.apereo.openregistry.model.TokenIdentifier
import org.apereo.openregistry.model.Type
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext

@Slf4j
@EqualsAndHashCode
class UpdateStandardizationProcessor implements OpenRegistryProcessor {
    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        log.info("Starting 'update standardization' processing phase with [$processorContext]")

        def person = processorContext.person
        def info = processorContext.request.contents

        // do names
        info?.sorAttributes?.names?.each { nameMap ->
            NameIdentifier nameIdentifier
            if (nameMap.id) {
                nameIdentifier = person.wallet.find {it.id == nameMap.id }
            } else {
                nameIdentifier = new NameIdentifier(
                        systemOfRecord: processorContext.request.systemOfRecord,
                        name: new Name()
                )
            }
            // selectively change attributes of the name
            if (nameMap.type) {
                nameIdentifier.type = Type.findByTargetAndValue(NameIdentifier, nameMap.type)
            }
            if (nameMap.prefix) {
                nameIdentifier.name.prefix = nameMap.prefix
            }
            if (nameMap.given) {
                nameIdentifier.name.givenName = nameMap.given
            }
            if (nameMap.middle) {
                nameIdentifier.name.middleName = nameMap.middle
            }
            if (nameMap.family) {
                nameIdentifier.name.familyName = nameMap.family
            }
            if (nameMap.suffix) {
                nameIdentifier.name.suffix = nameMap.suffix
            }
             if (nameMap.language) {
                 nameIdentifier.name.language = nameMap.language
             }

            // do this here, just in case there are collisions
            if (!nameMap.id) {
                person.addToWallet nameIdentifier
            }
        }

        // do identifiers
        info?.sorAttributes?.identifiers?.each { idMap ->
            TokenIdentifier tokenIdentifier
            if (idMap.id) {
                tokenIdentifier = person.wallet.find {it.id == idMap.id}
            } else {
                tokenIdentifier = new TokenIdentifier(
                        systemOfRecord: processorContext.request.systemOfRecord
                )
            }

            // selectively update attributes of token id
            if (idMap.type) {
                tokenIdentifier.type = Type.findByTargetAndValue(TokenIdentifier, idMap.type)
            }
            if (idMap.identifier) {
                tokenIdentifier.token = idMap.token
            }

            // do this here, just in case
            if (!idMap.id) {
                person.addToWallet tokenIdentifier
            }
        }

        // do email addresses
        info?.sorAttributes?.emailAddresses?.each { emailMap ->
            EmailAddressIdentifier emailAddressIdentifier
            if (emailMap.id) {
                emailAddressIdentifier = person.wallet.find { it.id == emailMap.id }
            } else {
                emailAddressIdentifier = new EmailAddressIdentifier(
                        systemOfRecord: processorContext.request.systemOfRecord
                )
            }

            // selectively update attributes of email address
            if (emailMap.type) {
                emailAddressIdentifier.type = Type.findByTargetAndValue(EmailAddressIdentifier, emailMap.type)
            }
            if (emailMap.address) {
                emailAddressIdentifier.emailAddress = emailMap.address
            }

            // do this here, just in case
            if (!emailMap.id) {
                person.addToWallet emailAddressIdentifier
            }
        }

        return processorContext
    }
}
