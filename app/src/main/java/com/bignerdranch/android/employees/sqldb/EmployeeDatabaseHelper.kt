package com.bignerdranch.android.employees.sqldb

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.bignerdranch.android.employees.model.Employee

class EmployeeDatabaseHelper(
    private val context: Context?,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_AGE + " INTEGER, " +
                COLUMN_PROFESSION + " TEXT);"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addEmployee(name: String, age: Int, prof: String) {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(COLUMN_NAME, name)
        cv.put(COLUMN_AGE, age)
        cv.put(COLUMN_PROFESSION, prof)
        val result = db.insert(TABLE_NAME, null, cv)
        if (result == -1L) {
            Toast.makeText(context, "Failed add to SQLiteDatabase", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, " Employee add to SQLiteDatabase", Toast.LENGTH_SHORT).show()
        }
    }

    fun getAllEmployeesSQL(): ArrayList<Employee> {
        val employeeList = arrayListOf<Employee>()
        val query = "SELECT * FROM $TABLE_NAME"
        val db = readableDatabase
        val cursor: Cursor?

        if (db != null) {
            cursor = db.rawQuery(query, null)
            if (cursor.count == 0) {
                Toast.makeText(context, " No data in SQLiteDatabase", Toast.LENGTH_SHORT).show()
            } else {
                cursor.use { cursor ->
                    while (cursor.moveToNext()) {
                        val id = cursor.getInt(0)
                        val name = cursor.getString(1)
                        val age = cursor.getInt(2)
                        val prof = cursor.getString(3)
                        employeeList.add(Employee(id, name, age, prof))
                    }
                }
            }
        }
        return employeeList
    }

    fun getAllEmployeesSQLByName(): ArrayList<Employee> {
        val employeeList = arrayListOf<Employee>()
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_NAME ASC"
        val db = readableDatabase
        val cursor: Cursor?

        if (db != null) {
            cursor = db.rawQuery(query, null)
            if (cursor.count == 0) {
                Toast.makeText(context, " No data in SQLiteDatabase", Toast.LENGTH_SHORT).show()
            } else {
                cursor.use { cursor ->
                    while (cursor.moveToNext()) {
                        val id = cursor.getInt(0)
                        val name = cursor.getString(1)
                        val age = cursor.getInt(2)
                        val prof = cursor.getString(3)
                        employeeList.add(Employee(id, name, age, prof))
                    }
                }
            }
        }
        return employeeList
    }

    fun getAllEmployeesSQLByAge(): ArrayList<Employee> {
        val employeeList = arrayListOf<Employee>()
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_AGE ASC"
        val db = readableDatabase
        val cursor: Cursor?

        if (db != null) {
            cursor = db.rawQuery(query, null)
            if (cursor.count == 0) {
                Toast.makeText(context, " No data in SQLiteDatabase", Toast.LENGTH_SHORT).show()
            } else {
                cursor.use { cursor ->
                    while (cursor.moveToNext()) {
                        val id = cursor.getInt(0)
                        val name = cursor.getString(1)
                        val age = cursor.getInt(2)
                        val prof = cursor.getString(3)
                        employeeList.add(Employee(id, name, age, prof))
                    }
                }
            }
        }
        return employeeList
    }

    fun getAllEmployeesSQLByProf(): ArrayList<Employee> {
        val employeeList = arrayListOf<Employee>()
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_PROFESSION ASC"
        val db = readableDatabase
        val cursor: Cursor?

        if (db != null) {
            cursor = db.rawQuery(query, null)
            if (cursor.count == 0) {
                Toast.makeText(context, " No data in SQLiteDatabase", Toast.LENGTH_SHORT).show()
            } else {
                cursor.use { cursor ->
                    while (cursor.moveToNext()) {
                        val id = cursor.getInt(0)
                        val name = cursor.getString(1)
                        val age = cursor.getInt(2)
                        val prof = cursor.getString(3)
                        employeeList.add(Employee(id, name, age, prof))
                    }
                }
            }
        }
        return employeeList
    }

    fun updateData(row_id: String, name: String, age: Int, prof: String) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_NAME, name)
        cv.put(COLUMN_AGE, age)
        cv.put(COLUMN_PROFESSION, prof)

        val result = db.update(
            TABLE_NAME,
            cv,
            "_id=?",
            arrayOf(row_id)
        )
        if (result == -1) {
            Toast.makeText(context, " Failed to Update!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, " Successfully Updated!", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteOneEmployee(row_id: String) {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, "_id=?", arrayOf(row_id))
        if (result == -1) {
            Toast.makeText(context, " Failed to Delete!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, " Successfully Deleted!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val DATABASE_NAME = "Employees.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "table_employees"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_NAME = "employee_name"
        private const val COLUMN_AGE = "employee_age"
        private const val COLUMN_PROFESSION = "employee_prof"
    }
}


