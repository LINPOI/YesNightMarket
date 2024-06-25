package com.example.yesnightmarket.Data


data class CreateTableRequest(
    val tableName: String,
    val columns: Map<String, Any>,
    val primaryKey: List<String>? = null
)

data class ColumnDefinition(
    val type: String,
    val foreign: ForeignKey? = null
)

data class ForeignKey(
    val table: String,
    val column: String
)
//val columns = mapOf(
//    "id" to ColumnDefinition("INT AUTO_INCREMENT PRIMARY KEY"),
//    "user_id" to ColumnDefinition(
//        type = "VARCHAR(255)",
//        foreign = ForeignKey(
//            table = "members",
//            column = "MemberGmail"
//        )
//    ),
//    "name" to ColumnDefinition("VARCHAR(255)")
//)
//
//val request = CreateTableRequest(
//    tableName = "my_table",
//    columns = columns,
//primaryKey = listOf("user_id", "order_id")
//)