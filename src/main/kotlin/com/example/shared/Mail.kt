package com.example.shared

import com.example.security.TokenGenerator
import com.example.shared.html.generatehtmlTemplate
import kotlinx.serialization.Serializable
import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.EmailException
import org.apache.commons.mail.HtmlEmail
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Serializable
object Mail {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    fun sendMail(userEmail: String, firstName: String): String? {
        try {
            val htmlEmail = HtmlEmail()
            htmlEmail.hostName = "smtp.googlemail.com"
            htmlEmail.setSmtpPort(587)
            htmlEmail.isSSLOnConnect = true
            htmlEmail.setDebug(true)
            htmlEmail.setAuthenticator(DefaultAuthenticator("isaacodei360@gmail.com", "pigvsxqgaseqislg"))
            htmlEmail.addTo(userEmail)
            htmlEmail.setFrom("isaacodei360@gmail.com", "PerezSite")
            htmlEmail.subject = "PerezSite - Account Verification"
            htmlEmail.setHtmlMsg(generatehtmlTemplate(firstName, TokenGenerator.getToken()))
            return htmlEmail.send()

        } catch (sql: EmailException) {
            log.warn("An error occurred when processing sale", sql)
            return null
        }

    }
}