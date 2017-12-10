package sarveshchavan777.triviaquiz


import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList
import android.net.ConnectivityManager


class TriviaQuizHelper(private val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        //OnCreate is called only once
        sqLiteDatabase.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        //OnUpgrade is called when ever we upgrade or increment our database version no
        sqLiteDatabase.execSQL(DROP_TABLE)
        onCreate(sqLiteDatabase)
    }

    fun deleteQuestionTable() {
        val db = this.writableDatabase
        db.delete(TABLE_NAME,null,null)
        db.close()
    }

    fun allQuestion() {
        val arraylist = ArrayList<TriviaQuestion>()
        for (data in HomeScreen.listQuestion) {
            var content = data.content
            val optA = data.questionA
            val optB = data.questionB
            val optC = data.questionC
            val optD = data.questionD
             var ans  = ""
            when {
                data.flagA == 1 -> ans = optA.toString()
                data.flagB == 1 -> ans = optB.toString()
                data.flagC == 1 -> ans = optC.toString()
                data.flagD == 1 -> ans = optD.toString()
            }
            arraylist.add(TriviaQuestion(content!!, optA!!, optB!!, optC!!, optD!!, ans))
        }
//        arraylist.add(TriviaQuestion("Test1?", "test1", "test2", "test3", "test4", "test1"))
//
//        arraylist.add(TriviaQuestion("Test2 ?", "Nhung", "Thanh", "Linh", "Pepper", "Test2"))
//
//        arraylist.add(TriviaQuestion("Test3 ?", "Loooooo", "Teeeeeest", "OOOOOOO", "Test3", "Test3"))
//
//        arraylist.add(TriviaQuestion("Test4 ?", "Mickey", "Sunny", "Test4", "Press", "Test4"))
//
//        arraylist.add(TriviaQuestion("Test5 ?", "Linh", "linh", "Test5", "Test2", "Test5"))
//
//        arraylist.add(TriviaQuestion("Long sentence test_Long sentence test_Long sentence test_Long sentence test_Long sentence test_Long sentence test", "Linh", "Long sentence test", "Linh", " Devi", "Long sentence test"))
//
//        arraylist.add(TriviaQuestion("Test7 C ?", "Linh", "Nhung", "Meo", "Test7", "Test7"))
//
//        arraylist.add(TriviaQuestion("Test8 ?", "Linh", "linh", "chocolate", "Test8", "Test8"))
//
//        arraylist.add(TriviaQuestion("Test9 ?", "Linh", "linh", "chocolate", "Test9", "Test9"))
//
//        arraylist.add(TriviaQuestion("Test10 ?", "Test10", "linh", "chocolate", "Test2", "Test10"))
//
//        arraylist.add(TriviaQuestion("Test11 ?", "Linh", "linh", "chocolate", "Test11", "Test11"))
//
//        arraylist.add(TriviaQuestion("Just give me a reason ?", "Linh", "linh", "chocolate", "Pjnk", "Pjnk"))
//
//        arraylist.add(TriviaQuestion("Test13 ?", "Linh", "linh", "chocolate", "Test13", "Test13"))
//
//        arraylist.add(TriviaQuestion("Test14 ?", "Linh", "Test14", "chocolate", "TestTest142", "Test14"))
//
//        arraylist.add(TriviaQuestion("Test15 ?", "Test15", "linh", "chocolate", "Test2", "Test15"))
//
//        arraylist.add(TriviaQuestion("Test16 ?", "Test16", "linh", "chocolate", "Test2", "Test16"))
//
//        arraylist.add(TriviaQuestion("Test17 ?", "Linh", "linh", "Test17", "Test2", "Test17"))
//
//        arraylist.add(TriviaQuestion("Test18 ?", "Linh", "Test18", "chocolate", "Test2", "Test18"))
//
//        arraylist.add(TriviaQuestion("Test19 ?", "Linh", "linh", "chocolate", "Test19", "Test19"))
//
//        arraylist.add(TriviaQuestion("Test20 ?", "Linh", "linh", "Test20", "Test2", "Test20"))

        this.addAllQuestions(arraylist)

    }


    private fun addAllQuestions(allQuestions: ArrayList<TriviaQuestion>) {
        val db = this.writableDatabase
        db.beginTransaction()
        try {
            val values = ContentValues()
            for (question in allQuestions) {
                values.put(QUESTION, question.question)
                values.put(OPTA, question.optA)
                values.put(OPTB, question.optB)
                values.put(OPTC, question.optC)
                values.put(OPTD, question.optD)
                values.put(ANSWER, question.answer)
                db.insert(TABLE_NAME, null, values)
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
            db.close()
        }
    }


    val allOfTheQuestions: List<TriviaQuestion>
        get() {

            val questionsList = ArrayList<TriviaQuestion>()
            val db = this.writableDatabase
            db.beginTransaction()
            val coloumn = arrayOf(UID, QUESTION, OPTA, OPTB, OPTC, OPTD, ANSWER)
            val cursor = db.query(TABLE_NAME, coloumn, null, null, null, null, null)


            while (cursor.moveToNext()) {
                val question = TriviaQuestion()
                question.setId(cursor.getInt(0))
                question.question = cursor.getString(1)
                question.optA = cursor.getString(2)
                question.optB = cursor.getString(3)
                question.optC = cursor.getString(4)
                question.optD = cursor.getString(5)
                question.answer = cursor.getString(6)
                questionsList.add(question)
            }

            db.setTransactionSuccessful()
            db.endTransaction()
            cursor.close()
            db.close()
            return questionsList
        }

    fun isNetworkConnected(context: Activity): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return cm.activeNetworkInfo != null
    }




    companion object {
        private val DB_NAME = "TQuiz.db"

        private val DB_VERSION = 3
        //Table name
        private val TABLE_NAME = "TQ"
        //Id of question
        private val UID = "_UID"
        //Question
        private val QUESTION = "QUESTION"
        //Option A
        private val OPTA = "OPTA"
        //Option B
        private val OPTB = "OPTB"
        //Option C
        private val OPTC = "OPTC"
        //Option D
        private val OPTD = "OPTD"
        //Answer
        private val ANSWER = "ANSWER"
        //So basically we are now creating table with first column-id , sec column-question , third column -option A, fourth column -option B , Fifth column -option C , sixth column -option D , seventh column - answer(i.e ans of  question)
        private val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ( $UID INTEGER PRIMARY KEY AUTOINCREMENT , $QUESTION VARCHAR(255), $OPTA VARCHAR(255), $OPTB VARCHAR(255), $OPTC VARCHAR(255), $OPTD VARCHAR(255), $ANSWER VARCHAR(255));"
        //Drop table query
        private val DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME

        fun isNetworkConnected(context: Activity): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            return cm.activeNetworkInfo != null
        }

        fun getProgressDialog(activity: Activity) : ProgressDialog{
            val progressDialog = ProgressDialog(activity)
            progressDialog.setProgressStyle(android.R.style.Theme_Translucent_NoTitleBar)
            progressDialog.setIcon(R.drawable.icon_smile)
            progressDialog.setMessage("Data is loading...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.setCancelable(false)
            progressDialog.isIndeterminate = true

            return progressDialog
        }

    }
}
