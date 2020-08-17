package com.gbancarel.adoptyourpet.repository.error

import java.lang.Exception

class ErrorStatusException (val reason: String) : Exception(reason)