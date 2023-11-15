package com.example.retrofittheory.builder

class MyRetrofit(private var name: String, private var age: Int, private var addr: String) {

    class Builder {
        private var name: String = ""
        private var age: Int = 0
        private var addr: String = ""

        fun age(age: Int): Builder {
            this.age = age
            return this
        }

        fun name(name: String): Builder {
            this.name = name
            return this
        }

        fun addr(addr: String): Builder {
            this.addr = addr
            return this
        }

        fun build() : MyRetrofit {
            return MyRetrofit(name, age, addr)
        }

    }

}