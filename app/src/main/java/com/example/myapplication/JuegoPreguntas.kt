package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@Preview(showBackground = true)
@Composable
fun JuegoPreguntas(){

    data class Pregunta(val enunciado: String, val respuesta: Boolean, val imagen: Int)

    var listaPreguntas = remember { mutableStateListOf<Pregunta>(
        Pregunta("¿Es de color blanco el caballo blanco de santiago?",true,
            R.drawable.caballoblanco),
        Pregunta("¿Las estrellas de mar no tienen cerebro?",true,
            R.drawable.estrellamar),
        Pregunta("¿Una vaca puede subir escaleras pero no puede bajarlas?",true,
            R.drawable.vacaescalera),
        Pregunta("¿Los delfines son los animales más inteligentes después de los humanos?",false,
            R.drawable.delfinsombrero)
        )}

    val listaPreguntasRecordar by remember { mutableStateOf(listaPreguntas)}

    var botonVerdadero by remember { mutableStateOf(Color.Blue) }
    var botonFalso by remember { mutableStateOf(Color.Blue) }
    var textoAciertoFallo by remember { mutableStateOf("") }

    var desactivarBoton by remember { mutableStateOf(true) }

    var randomValue by remember { mutableStateOf(Random.nextInt(0, listaPreguntas.size))}
    var imagenPregunta by remember { mutableStateOf(listaPreguntas.get(randomValue).imagen) }
    var textoPregunta by remember { mutableStateOf(listaPreguntas.get(randomValue).enunciado) }

    var resultadoFinalAciertos by remember { mutableStateOf(0)}
    var resultadoFinalFallos by remember { mutableStateOf(0)}

    var isButtonVisible by remember { mutableStateOf(true) }
    var textoSiguiente by remember { mutableStateOf("-> Siguiente") }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        if (listaPreguntas.size != 0) {
            Text(textoPregunta,
                fontSize = 30.sp,
                textAlign = TextAlign.Center)
        }

        Image(painter = painterResource(id = imagenPregunta),
            contentDescription = "",
            modifier = Modifier
                .size(500.dp))

        Text(textoAciertoFallo,
            fontSize = 20.sp)

        Row() {
            if (isButtonVisible) {
            Button(onClick = { /*TODO*/
                var respuesta : Boolean = true
                desactivarBoton = false
                if (listaPreguntas.size != 1) {
                    if (listaPreguntas.get(randomValue).respuesta == respuesta) {
                        botonVerdadero = Color.Green
                        textoAciertoFallo = "Has acertado"
                        resultadoFinalAciertos += 1
                    } else {
                        botonVerdadero = Color.Red
                        textoAciertoFallo = "Has fallado"
                        resultadoFinalFallos += 1
                    }
                    listaPreguntas.remove(listaPreguntas.get(randomValue))
                }
                             },
                enabled = desactivarBoton,
                colors = ButtonDefaults.buttonColors(
                    containerColor = botonVerdadero,
                    disabledContainerColor = botonVerdadero,
                    disabledContentColor = Color.White
                ))
            {
                Text(text = "Verdadero",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }}

            if (isButtonVisible) {
                Button(onClick = { /*TODO*/
                    var respuesta : Boolean = false
                    if (listaPreguntas.size != 1) {
                        if (listaPreguntas.get(randomValue).respuesta == respuesta) {
                            botonFalso = Color.Green
                            textoAciertoFallo = "Has acertado"
                            resultadoFinalAciertos += 1
                        } else {
                            botonFalso = Color.Red
                            textoAciertoFallo = "Has fallado"
                            resultadoFinalFallos += 1
                        }
                        listaPreguntas.remove(listaPreguntas.get(randomValue))
                        desactivarBoton = false
                    }
                            },
                    enabled = desactivarBoton,
                    colors = ButtonDefaults.buttonColors(containerColor = botonFalso,
                        disabledContainerColor = botonFalso,
                        disabledContentColor = Color.White
                    ))
                {
                    Text(text = "Falso",
                        fontSize = 20.sp)
                }
            }
        }

        Button(onClick = { /*TODO*/
            if (listaPreguntas.size != 1) {
                randomValue = Random.nextInt(0, listaPreguntas.size)
                desactivarBoton = true
                botonFalso = Color.Blue
                botonVerdadero = Color.Blue
                textoAciertoFallo = ""
                imagenPregunta = listaPreguntas.get(randomValue).imagen
                textoPregunta = listaPreguntas.get(randomValue).enunciado
            } else {
                isButtonVisible = false
                textoAciertoFallo = "Se ha terminado la partida"
                textoPregunta = "Aciertos: "+resultadoFinalAciertos+
                        "\n\nFallos: "+resultadoFinalFallos
                desactivarBoton = false
                if (resultadoFinalAciertos >= resultadoFinalFallos) {
                    imagenPregunta = R.drawable.pulgararriba
                    textoPregunta += "\n\nEnhorabuena has ganado"
                } else {
                    imagenPregunta = R.drawable.pulgarabajo
                    textoPregunta += "\n\nHas perdido, aprende mas sobre animales"
                }
                if (textoSiguiente.equals("Reiniciar")) {

                    listaPreguntas.clear()
                    listaPreguntas.addAll(listaPreguntasRecordar)
                    isButtonVisible = true
                    textoSiguiente = listaPreguntas.size.toString()
                    //listaPreguntasRecordar.clear()
                } else {
                    textoSiguiente = "Reiniciar"
                }
            }
                         },
            Modifier.fillMaxWidth()) {
            Text(text = textoSiguiente,
                fontSize = 20.sp)
        }
    }
}