package adventofcode2020

import java.io.File
import java.nio.file.Paths

fun readFileLineByLine(fileName: String, action: (String) -> Unit) =
    File(Paths.get("resources/$fileName").toAbsolutePath().toUri()).forEachLine { action.invoke(it) }