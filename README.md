# 🎨 Proyecto MVC – Editor de Figuras Geométricas en Java

Este proyecto implementa una aplicación en **Java** siguiendo el patrón **Modelo–Vista–Controlador (MVC)**.  
La aplicación permite crear, gestionar y dibujar figuras geométricas como círculos, líneas y polígonos, además de exportarlas a formato SVG.

---

## 📁 Estructura del Proyecto

<img width="329" height="623" alt="image" src="https://github.com/user-attachments/assets/c92b4b15-de0c-4a31-bf58-c1bc09a6fb0d" />


---

## 🧠 Modelo

Contiene la lógica del programa:

- **Figura.java**: clase base para figuras geométricas.  
- **Circulo, Linea, PoligonoRegular, PoligonoIrregular**: implementaciones específicas.  
- **Dibujo.java**: conjunto de figuras.  
- **DibujoDAO / FiguraDAO**: persistencia opcional.  
- **SVGExporter**: exportación del dibujo.  
- **Validacion**: validación de parámetros.  
- **ConexionBD**: conexión a base de datos.

---

## 🪟 Vista

- **Lienzo.java**: panel donde se dibujan las figuras.  
- **VistaPaint.java**: interfaz gráfica del editor.

---

## 🎮 Controlador

- **PaintControlador.java**: gestiona la interacción entre la vista y el modelo.

---

## ✨ Funcionalidades

✔ Dibujar círculos, líneas y polígonos  
✔ Manipular y almacenar figuras en un dibujo  
✔ Exportar el resultado a **SVG**  
✔ Validación de datos de entrada  
✔ Interfaz gráfica con herramientas de dibujo  

---


---

## 🎯 Objetivo

Este proyecto demuestra el uso correcto del patrón MVC aplicado a una aplicación gráfica interactiva, separando de forma clara:

- **Modelo** → lógica interna  
- **Vista** → representación gráfica  
- **Controlador** → flujo de interacción usuario–sistema  

---

## 📄 Archivo SVG de prueba

El repositorio incluye `test.svg` como ejemplo de salida generada.

---




