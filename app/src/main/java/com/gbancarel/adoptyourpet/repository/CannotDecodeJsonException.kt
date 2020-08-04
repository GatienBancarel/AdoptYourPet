package com.gbancarel.adoptyourpet.repository

import java.lang.Exception

class CannotDecodeJsonException (val reason: String) : Exception(reason)
