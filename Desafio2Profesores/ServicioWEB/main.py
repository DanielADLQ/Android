import conexionOO
import json
import Persona
from flask import Flask, request, jsonify
from flask_restful import Resource, Api

#Comenta y/o descomenta cada bloque para probar cada apartado.
#La base de datos la puedes encontrar en la carpeta del proyecto, solo tienes que importarla
#y cambiar las credenciales por las tuyas.
#https://blog.nearsoftjobs.com/crear-un-api-y-una-aplicaci%C3%B3n-web-con-flask-6a76b8bf5383
#https://content.breatheco.de/es/lesson/building-apis-with-python-flask

#pip install Flask
#pip install flask_restful

################################### Probando conexión ####################################
conex = conexionOO.Conexion('root','','desafio2pmdm')
# conex.cerrarConexion()


################################### Probando inserción ###################################
#Dentro del método insertar se realiza una apertura y un cerrado de la conexión.
# dni = input("DNI? ")
# nombre = input("Nombre? ")
# clave = input("Clave? ")
# tefno = input("Teléfono? ")
# resultado = conex.insertarPersona(dni, nombre, clave, tefno)
# if(resultado == -1):
#     print("Problema al insertar el registro.")
# else:
#     print("Registro insertado con éxito.")
    

################################ Probando select de todos #######################################
# Dentro del método seleccionar se realiza una apertura y un cerrado de la conexión.
# listaPersonas = conex.seleccionarTodos()
# if (len(listaPersonas) != 0):
#     for pe in listaPersonas:
#         print(pe)
#         #print("DNI: " + pe[0] + ", nombre: " + pe[1])
# else:
#     print("No se han extraído datos.")




################################# Buscando por DNI ###############################################
#Dentro del método seleccionar se realiza una apertura y un cerrado de la conexión.
# dni = input("DNI a buscar? ")
# listaPersonas = conex.buscarDNI(dni)
# if (len(listaPersonas) != 0):
#     for pe in listaPersonas:
#         print(pe)
#         print("DNI: " + pe[0] + ", nombre: " + pe[1])
# else:
#     print("No se han encontrado datos")
    
    
############################### Probando cambiar la clave de un dni ###################################
#Dentro del método cambiarClave se realiza una apertura y un cerrado de la conexión.
# dniCambiar = input("DNI a cambiar? ")
# nuevaClave = input("Nueva clave? ")
# resultado = conex.cambiarClave(dniCambiar, nuevaClave)
# if (resultado == 0):
#     print("Clave cambiada.")
# else:
#     print("No se han encontrado datos")
    

##################################### Probando borrar por dni #########################################
#Dentro del método birrarDNI se realiza una apertura y un cerrado de la conexión.
# dniBorrar = input("DNI a borrar? ")
# resultado = conex.borrarDNI(dniBorrar)
# if (resultado == 0):
#     print("Registro borrado.")
# else:
#     print("No se han borrado datos")

app = Flask(__name__)
api = Api(app)

#------------------------------------------------------------------------------
@app.route('/')
def hello():
    return 'Bienvenido a editar aulas'

#------------------------------------------------------------------------------
#https://blog.miguelgrinberg.com/post/running-your-flask-application-over-https
#pip install pyopenssl  --> para montarlo sobre https.
@app.route("/profesores", methods=['GET']) #aquí especificamos la ruta para el endpoint.
def getProfesores(): #aquí declaramos una función que se llamará cuando se realice una request a esa url
    listaProfesores = conex.seleccionarTodosProfesores()
    print(listaProfesores)
    if (len(listaProfesores) != 0):
        resp = jsonify(listaProfesores)
        resp.status_code = 200
    else:
        respuesta = {'message': 'No se han extraido datos.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(resp)
    return resp

@app.route("/aulas", methods=['GET']) #aquí especificamos la ruta para el endpoint.
def getAulas(): #aquí declaramos una función que se llamará cuando se realice una request a esa url
    listaAulas = conex.seleccionarTodosAulas()
    print(listaAulas)
    if (len(listaAulas) != 0):
        resp = jsonify(listaAulas)
        resp.status_code = 200
    else:
        respuesta = {'message': 'No se han extraido datos.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(resp)
    return resp

@app.route("/ordenadores", methods=['GET']) #aquí especificamos la ruta para el endpoint.
def getOrdenadores(): #aquí declaramos una función que se llamará cuando se realice una request a esa url
    listaOrd = conex.seleccionarTodosOrdenadores()
    print(listaOrd)
    if (len(listaOrd) != 0):
        resp = jsonify(listaOrd)
        resp.status_code = 200
    else:
        respuesta = {'message': 'No se han extraido datos.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(resp)
    return resp

#------------------------------------------------------------------------------
@app.route("/profesores/<codigo>", methods=['GET']) #aquí especificamos la ruta para el endpoint.
def getProfesor(codigo): #aquí declaramos una función que se llamará cuando se realice una request a esa url
    print(codigo)
    listaProfesores = conex.buscarCodigoProfesor(codigo)
    print(jsonify(listaProfesores))
    if (len(listaProfesores) != 0):
        resp = jsonify(listaProfesores)
        resp.status_code = 200
    else:
        respuesta = {'message': 'No se han extraido datos.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(resp)
    return resp

#------------------------------------------------------------------------------
@app.route("/aulas/<codaula>", methods=['GET']) #aquí especificamos la ruta para el endpoint.
def getAula(codaula): #aquí declaramos una función que se llamará cuando se realice una request a esa url
    print(codaula)
    listaAulas = conex.buscarCodigoAula(codaula)
    print(jsonify(listaAulas))
    if (len(listaAulas) != 0):
        resp = jsonify(listaAulas)
        resp.status_code = 200
    else:
        respuesta = {'message': 'No se han extraido datos.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(resp)
    return resp

#------------------------------------------------------------------------------
@app.route("/ordenadores/<codord>", methods=['GET']) #aquí especificamos la ruta para el endpoint.
def getOrdenador(codord): #aquí declaramos una función que se llamará cuando se realice una request a esa url
    print(codord)
    listaOrd = conex.buscarCodigoOrdenador(codord)
    print(jsonify(listaOrd))
    if (len(listaOrd) != 0):
        resp = jsonify(listaOrd)
        resp.status_code = 200
    else:
        respuesta = {'message': 'No se han extraido datos.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(resp)
    return resp

#------------------------------------------------------------------------------
@app.route("/aulas/<codaula>/ordenadores", methods=['GET']) #aquí especificamos la ruta para el endpoint.
def getOrdenadoresAula(codaula): #aquí declaramos una función que se llamará cuando se realice una request a esa url
    print(codaula)
    listaOrd = conex.buscarOrdenadoresAula(codaula)
    print(jsonify(listaOrd))
    if (len(listaOrd) != 0):
        resp = jsonify(listaOrd)
        resp.status_code = 200
    else:
        respuesta = {'message': 'No se han extraido datos.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(resp)
    return resp


#------------------------------------------------------------------------------
@app.route("/profesores/registrar", methods=["POST"])
def addProfesor():
    data = request.json
    print(data) #Desde Android nos llega en formato diccionario.
    print(data['codigo'])
    print(data['nya'])
    print(data['rol'])
    if (conex.insertarProfesor(data['codigo'],data['nya'],data['rol'],data['clave'])==0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'Clave duplicada.'}
        resp = jsonify(respuesta)
        resp.status_code = 400

    print(resp)
    return resp

#------------------------------------------------------------------------------
@app.route("/aulas/registrar", methods=["POST"])
def addAula():
    data = request.json
    print(data) #Desde Android nos llega en formato diccionario.
    print(data['codaula'])
    print(data['nomaula'])
    if (conex.insertarAula(data['codaula'],data['nomaula'])==0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'Clave duplicada.'}
        resp = jsonify(respuesta)
        resp.status_code = 400

    print(resp)
    return resp

#------------------------------------------------------------------------------
@app.route("/ordenadores/registrar", methods=["POST"])
def addOrdenador():
    data = request.json
    print(data) #Desde Android nos llega en formato diccionario.
    print(data['codord'])
    print(data['cpu'])
    print(data['ram'])
    print(data['almacenamiento'])
    print(data['aula'])
    if (conex.insertarOrdenador(data['codord'],data['cpu'],data['ram'],data['almacenamiento'],data['aula'])==0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'Clave duplicada.'}
        resp = jsonify(respuesta)
        resp.status_code = 400

    print(resp)
    return resp

#------------------------------------------------------------------------------
@app.route("/profesores/borrar/<codigo>", methods=["DELETE"])
def delProfesor(codigo):

    if (conex.borrarProfesor(codigo)>0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'codigo ' + str(codigo) + ' no encontrado.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(respuesta)
    print(resp)
    return resp

#------------------------------------------------------------------------------
@app.route("/aulas/borrar/<codaula>", methods=["DELETE"])
def delAula(codaula):

    if (conex.borrarAula(codaula)>0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'codigo de aula ' + str(codaula) + ' no encontrado.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(respuesta)
    print(resp)
    return resp

#------------------------------------------------------------------------------
@app.route("/ordenadores/borrar/<codord>", methods=["DELETE"])
def delOrdenador(codord):

    if (conex.borrarOrdenador(codord)>0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'codigo de ordenador ' + str(codord) + ' no encontrado.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(respuesta)
    print(resp)
    return resp

#------------------------------------------------------------------------------
@app.route("/profesores/modificar", methods=["PUT"])
def modProfesor():
    data = request.json
    print(data) #Desde Android nos llega en formato diccionario.
    print(data['codigo'])
    print(data['nya'])
    print(data['rol'])
    if (conex.modificarProfesor(data['codigo'],data['nya'],data['rol']) > 0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'Error al modificar.'}
        resp = jsonify(respuesta)
        resp.status_code = 400

    print(resp)
    return resp

#------------------------------------------------------------------------------
@app.route("/aulas/modificar", methods=["PUT"])
def modAula():
    data = request.json
    print(data) #Desde Android nos llega en formato diccionario.
    print(data['codaula'])
    print(data['nomaula'])
    if (conex.modificarAula(data['codaula'],data['nomaula']) > 0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'Error al modificar.'}
        resp = jsonify(respuesta)
        resp.status_code = 400

    print(resp)
    return resp

#------------------------------------------------------------------------------
@app.route("/ordenadores/modificar", methods=["PUT"])
def modOrdenador():
    data = request.json
    print(data) #Desde Android nos llega en formato diccionario.
    print(data['codord'])
    print(data['cpu'])
    print(data['ram'])
    print(data['almacenamiento'])
    print(data['aula'])
    if (conex.modificarOrdenador(data['codord'],data['cpu'],data['ram'],data['almacenamiento'],data['aula']) > 0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'Error al modificar.'}
        resp = jsonify(respuesta)
        resp.status_code = 400

    print(resp)
    return resp


# Para montarlo en http normaleras.
if __name__ == '__main__':
    #app.run(debug=True)
    app.run(debug=True, host= '0.0.0.0') #Esto sería para poder usar el móvil. No arrancaría el servicio en localhost sino en esa ip. También 0.0.0.0

# Esto es para montarlo en https.
# if __name__ == "__main__":
#     app.run(ssl_context='adhoc')
