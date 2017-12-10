package sarveshchavan777.triviaquiz

import android.app.Activity

class TriviaQuestion : Activity {
    private var id: Int = 0
    var question: String? = null
    var optA: String? = null
    var optB: String? = null
    var optC: String? = null
    var optD: String? = null
    var answer: String? = null

    constructor(q: String, oa: String, ob: String, oc: String, od: String, ans: String) {

        question = q
        optA = oa
        optB = ob
        optC = oc
        optD = od
        answer = ans
    }

    constructor() {
        id = 0
        question = ""
        optA = ""
        optB = ""
        optC = ""
        optD = ""
        answer = ""
    }

    fun setId(i: Int) {
        id = i
    }


}
