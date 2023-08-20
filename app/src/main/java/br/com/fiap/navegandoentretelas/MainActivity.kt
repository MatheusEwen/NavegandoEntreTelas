package br.com.fiap.navegandoentretelas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import androidx.compose.ui.Modifier

import androidx.navigation.NavType

import androidx.navigation.navArgument
import br.com.fiap.navegandoentretelas.screens.LoginScreen
import br.com.fiap.navegandoentretelas.screens.MenuScreen
import br.com.fiap.navegandoentretelas.screens.PedidosScreen
import br.com.fiap.navegandoentretelas.screens.PerfilScreen
import br.com.fiap.navegandoentretelas.ui.theme.NavegandoEntreTelasTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.animation.composable

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavegandoEntreTelasTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background)
                {
                    val navController = rememberAnimatedNavController()
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = "login",
                        exitTransition = {
                            slideOutOfContainer(towards = AnimatedContentScope.SlideDirection.End,
                            animationSpec = tween(1000)
                            ) + fadeOut(animationSpec = tween(1000))
                        },
                        enterTransition = {
                            slideIntoContainer(towards = AnimatedContentScope.SlideDirection.Start,
                            animationSpec = tween(1000))
                        }
                    ){
                        composable(route = "login"){ LoginScreen(navController)}
                        composable(route = "menu"){ MenuScreen(navController)}
                        composable(
                            route = "pedidos?cliente={cliente}",
                            arguments = listOf(navArgument(name = "cliente"){
                                defaultValue = "Sem cliente"
                            })
                        ){

                            PedidosScreen(navController, it.arguments?.getString("cliente"))
                        }
                        composable(
                            route = "perfil/{nome}/{idade}",
                            arguments = listOf(
                                navArgument("nome"){type = NavType.StringType},
                                navArgument("idade"){type = NavType.IntType}
                            )
                        ){
                            val nome: String? = it.arguments?.getString("nome", "")
                            val idade: Int? = it.arguments?.getInt("idade", 0)
                            PerfilScreen(navController, nome!!, idade!!) //double bang
                        }
                    }
                }
                }
            }
        }
    }


