package com.gbancarel.adoptyourpet.repository.error

import java.lang.Exception

class NoInternetConnectionAvailable (val reason: String) : Exception(reason)