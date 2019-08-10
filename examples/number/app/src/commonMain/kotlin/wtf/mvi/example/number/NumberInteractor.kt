package wtf.mvi.example.number

class NumberInteractor {

    val number = Source(0)

    fun increase() {
        number.value++
    }

    fun decrease() {
        number.value--
    }

}