package com.perezsite.services.databaseconfigurations

import org.slf4j.LoggerFactory
import java.io.InputStream
import java.util.*

object MysqlConfiguration {
    private var properties = Properties()
    private lateinit var inputStream: InputStream
    private val log = LoggerFactory.getLogger(this::class.java)
    fun loadPopsFromHoconFile() {
        val hoconFile = this::class.java.classLoader.getResource("application.conf")
        log.info("Searching for hocon file...")
        hoconFile?.let {
            log.info("File found")
            log.info("Loading from Hocon file...")
            inputStream = it.openStream()
            properties.load(inputStream)

        }
    }

    fun loadPopsFromAppPropsFile() {
        val propsFile = this::class.java.classLoader.getResource("application.properties")
        log.info("Searching for Application property file...")
        propsFile?.let {
            log.info("File found")
            log.info("Loading from Application config file...")
            inputStream = it.openStream()
            properties.load(inputStream)
        }
    }

    fun getSystemProperties(): Properties = properties
}