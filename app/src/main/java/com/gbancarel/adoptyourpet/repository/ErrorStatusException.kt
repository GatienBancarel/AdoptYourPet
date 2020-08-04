package com.gbancarel.adoptyourpet.repository

import java.lang.Exception

class ErrorStatusException (val reason: String) : Exception(reason)