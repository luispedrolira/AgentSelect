package com.proyecto2.AppRecomendaciones;

import java.util.Scanner;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try (EmbeddedNeo4j neo4j = new EmbeddedNeo4j("bolt://localhost:7687", "neo4j", "password")) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Menú de Recomendaciones");
                System.out.println("1. Recomendar agentes por rol");
                System.out.println("2. Recomendar agentes por habilidad");
                System.out.println("3. Recomendar usuarios por personajes en común");
                System.out.println("4. Salir");
                System.out.print("Elige una opción: ");
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea

                switch (opcion) {
                    case 1:
                        System.out.print("Introduce el rol: ");
                        String rol = scanner.nextLine();
                        List<String> agentesPorRol = neo4j.recomendarAgentesPorRol(rol);
                        System.out.println("Agentes con el rol " + rol + ": " + agentesPorRol);
                        break;
                    case 2:
                        System.out.print("Introduce la habilidad: ");
                        String habilidad = scanner.nextLine();
                        List<String> agentesPorHabilidad = neo4j.recomendarAgentesPorHabilidad(habilidad);
                        System.out.println("Agentes con la habilidad " + habilidad + ": " + agentesPorHabilidad);
                        break;
                    case 3:
                        System.out.print("Introduce el nombre del usuario: ");
                        String usuario = scanner.nextLine();
                        List<String> usuariosPorPersonajesEnComun = neo4j.recomendarUsuariosPorPersonajesEnComun(usuario);
                        System.out.println("Usuarios que usan personajes en común con " + usuario + ": " + usuariosPorPersonajesEnComun);
                        break;
                    case 4:
                        System.out.println("Saliendo...");
                        return;
                    default:
                        System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            }
        }
    }
}



