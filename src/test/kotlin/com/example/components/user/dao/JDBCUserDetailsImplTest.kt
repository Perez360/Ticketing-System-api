package com.example.components.user.dao

import User
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import uk.co.jemos.podam.api.PodamFactoryImpl

@TestInstance(TestInstance.Lifecycle.PER_CLASS) //This annotation ask Junit5 to create only one instance of the class TradePostServiceTest and use it to perform the tests
internal class JDBCUserDetailsImplTest {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    private var factory: PodamFactoryImpl = PodamFactoryImpl()
    private lateinit var userList: List<User>
    private lateinit var underTest: JDBCUserDetailsImpl

    @BeforeAll
    fun setUp() {
        //DatabaseFactory.init()
        underTest = JDBCUserDetailsImpl()
        userList = factory.manufacturePojoWithFullData(List::class.java, User::class.java) as List<User>
        userList.forEach { user: User ->
            //underTest.create(user)
            println("lkhbihkjhilkjnkbhik")
        }
    }

    @AfterAll
    fun tearDown() {
        transaction {
            SchemaUtils.drop()
        }
    }

    @Test
    fun create() {
        //GIVEN
        val oneUser = factory.manufacturePojoWithFullData(User::class.java)
        //WHEN
        //val expected = underTest.create(oneUser)
        //THEN
            //assertThat(expected).isEqualTo(1)
    }

    @Test
    fun get() {
    }

    @Test
    fun delete() {
    }

    @Test
    fun update() {
    }

    @Test
    fun list() {
    }
}