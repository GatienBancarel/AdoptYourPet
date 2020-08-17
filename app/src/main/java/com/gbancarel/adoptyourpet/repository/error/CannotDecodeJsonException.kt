package com.gbancarel.adoptyourpet.repository.error

import java.lang.Exception

class CannotDecodeJsonException (val reason: String) : Exception(reason)
