package io.github.sgpublic.uniktx.logback.util

import org.slf4j.Logger

interface Loggable

expect val Loggable.log: Logger