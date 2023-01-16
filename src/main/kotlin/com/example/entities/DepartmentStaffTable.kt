package com.example.entities

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object DepartmentStaffTable : Table("department_staff") {
    val id: Column<Int> = integer("id").autoIncrement()
    val departmentId: Column<Int> = integer("department_id")
        .references(
            DepartmentTable.id,
            ReferenceOption.CASCADE,
            ReferenceOption.CASCADE,
            "c_fk_department_staff_department_id"
        )
    val staffId: Column<Int> = integer("staff_id")
        .references(
            DepartmentTable.id,
            ReferenceOption.CASCADE,
            ReferenceOption.CASCADE,
            "c_fk_department_staff_staff_id"
        )
    val isPrivate: Column<Boolean> = bool("is_private").clientDefault { false }
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}