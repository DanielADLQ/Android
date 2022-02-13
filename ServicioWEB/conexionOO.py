import pymysql
import json
import Persona
from Persona import Persona, PersonaEncoder
#La librería se instala con el comando: pip install pymysql
class Conexion:
    
    def __init__(self, usuario, passw, bd):
        self._usuario = usuario
        self._passw = passw
        self._bd = bd

        try:
            #Abrimos y cerramos la bd para dos cosas: comprobar que los datos de conexión son correctos y
            #dar el tipo adecuado a la variable self._conexion.
            self._conexion = pymysql.connect(host='localhost',
                                    user=self._usuario,
                                    password=self._passw,
                                    db=self._bd)
            self._conexion.close()
            print("Datos de conexión correctos.")
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            print("Ocurrió un error con los datos de conexión: ", e)
        
         
    def conectar(self):
        """Devuelve la variable conexion o -1 si la conexión no ha sido correcta."""
        try:
            self._conexion = pymysql.connect(host='localhost',
                                    user=self._usuario,
                                    password=self._passw,
                                    db=self._bd)
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return -1

    def cerrarConexion(self):
        self._conexion.close()
        
        
    def insertarProfesor(self, codigo, nya, rol, clave):
        try:
            self.conectar()
            cursor =  self._conexion.cursor()
            consulta = "INSERT INTO profesor(codigo, nya, rol, clave) VALUES (%s, %s, %s, %s)"
            cursor.execute(consulta, (codigo, nya, rol, clave))
            self._conexion.commit()
            self.cerrarConexion()
            return 0
        except (pymysql.err.IntegrityError) as e:
            # print("Ocurrió un error al insertar: clave duplicada.", e)
            return -1

    def insertarAula(self, codaula, nomaula):
        try:
            self.conectar()
            cursor =  self._conexion.cursor()
            consulta = "INSERT INTO aula(codaula, nomaula) VALUES (%s, %s)"
            cursor.execute(consulta, (codaula, nomaula))
            self._conexion.commit()
            self.cerrarConexion()
            return 0
        except (pymysql.err.IntegrityError) as e:
            # print("Ocurrió un error al insertar: clave duplicada.", e)
            return -1

    def insertarOrdenador(self, codord, cpu, ram, almacenamiento, aula):
        try:
            self.conectar()
            cursor =  self._conexion.cursor()
            consulta = "INSERT INTO ordenador(codord, cpu, ram, almacenamiento, aula) VALUES (%s, %s, %s, %s, %s)"
            cursor.execute(consulta, (codord, cpu, ram, almacenamiento, aula))
            self._conexion.commit()
            self.cerrarConexion()
            return 0
        except (pymysql.err.IntegrityError) as e:
            # print("Ocurrió un error al insertar: clave duplicada.", e)
            return -1


    #https://stackoverflow.com/questions/3286525/return-sql-table-as-json-in-python
    def seleccionarTodosProfesores(self):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                # En este caso no necesitamos limpiar ningún dato
                cursor.execute("SELECT codigo, nya, rol, clave FROM profesor")
                r = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row in cursor.fetchall()]
                #print(r)
                self.cerrarConexion()
                return r
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return []

    def seleccionarTodosAulas(self):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                # En este caso no necesitamos limpiar ningún dato
                cursor.execute("SELECT codaula, nomaula FROM aula")
                r = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row in cursor.fetchall()]
                #print(r)
                self.cerrarConexion()
                return r
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return []

    def seleccionarTodosOrdenadores(self):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                # En este caso no necesitamos limpiar ningún dato
                cursor.execute("SELECT codord, cpu, ram, almacenamiento, aula FROM ordenador")
                r = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row in cursor.fetchall()]
                #print(r)
                self.cerrarConexion()
                return r
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return []


    #https://www.programcreek.com/python/example/104689/sklearn.datasets.fetch_20newsgroups
    #https://stackoverflow.com/questions/11280382/object-is-not-json-serializable
    def buscarCodigoProfesor(self, codigo):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                # En este caso no necesitamos limpiar ningún dato
                cursor.execute("SELECT codigo, nya, rol, clave FROM profesor WHERE codigo = %s", (codigo))
                
                r = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row in cursor.fetchall()]
                # print(r)
                # print("----")
                print(r)
                # objJson = r[0]
                # # Con fetchall traemos todas las filas
                # pers = cursor.fetchall()
                # # Recorrer e imprimir
                # for row in pers:
                #     p = Persona(row[0],row[1],row[2],row[3])
                # objJson = json.dumps(p, cls=PersonaEncoder, indent=4)
                # #print(objJson)
                
                self.cerrarConexion()
                
                
                if (r):
                    return r[0]
                else:
                    return []
                
                
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return []


    def buscarCodigoAula(self, codaula):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                # En este caso no necesitamos limpiar ningún dato
                cursor.execute("SELECT codaula, nomaula FROM aula WHERE codaula = %s", (codaula))

                r = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row in cursor.fetchall()]
                # print(r)
                # print("----")
                print(r)
                # objJson = r[0]
                # # Con fetchall traemos todas las filas
                # pers = cursor.fetchall()
                # # Recorrer e imprimir
                # for row in pers:
                #     p = Persona(row[0],row[1],row[2],row[3])
                # objJson = json.dumps(p, cls=PersonaEncoder, indent=4)
                # #print(objJson)

                self.cerrarConexion()


                if (r):
                    return r[0]
                else:
                    return []

        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return []


    def buscarCodigoOrdenador(self, codord):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                # En este caso no necesitamos limpiar ningún dato
                cursor.execute("SELECT codord, cpu, ram, almacenamiento, aula FROM ordenador WHERE codord = %s", (codord))

                r = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row in cursor.fetchall()]
                # print(r)
                # print("----")
                print(r)
                # objJson = r[0]
                # # Con fetchall traemos todas las filas
                # pers = cursor.fetchall()
                # # Recorrer e imprimir
                # for row in pers:
                #     p = Persona(row[0],row[1],row[2],row[3])
                # objJson = json.dumps(p, cls=PersonaEncoder, indent=4)
                # #print(objJson)

                self.cerrarConexion()


                if (r):
                    return r[0]
                else:
                    return []

        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return []


    def buscarOrdenadoresAula(self, aula):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                # En este caso no necesitamos limpiar ningún dato
                cursor.execute("SELECT codord, cpu, ram, almacenamiento, aula FROM ordenador WHERE aula = %s", (aula))
                r = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row in cursor.fetchall()]
                #print(r)
                self.cerrarConexion()
                return r
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return []


    def cambiarClave(self, codigo, nuevaClave):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                consulta = "UPDATE profesor SET clave = %s WHERE codigo = %s;"
                cursor.execute(consulta, (nuevaClave, codigo))

            # No olvidemos hacer commit cuando hacemos un cambio a la BD
            self._conexion.commit()
            self.cerrarConexion()
            return 0
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return -1
        
        
    #https://pynative.com/python-mysql-delete-data/
    def borrarProfesor(self, codigo):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                consulta = "DELETE FROM profesor WHERE codigo = %s;"
                cursor.execute(consulta, (codigo))

                # No olvidemos hacer commit cuando hacemos un cambio a la BD
                self._conexion.commit()
                self.cerrarConexion()
                return cursor.rowcount #Registros afectados en el borrado.
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return -1

    def borrarAula(self, codaula):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                consulta = "DELETE FROM aula WHERE codaula = %s;"
                cursor.execute(consulta, (codaula))

                # No olvidemos hacer commit cuando hacemos un cambio a la BD
                self._conexion.commit()
                self.cerrarConexion()
                return cursor.rowcount #Registros afectados en el borrado.
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return -1

    def borrarOrdenador(self, codord):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                consulta = "DELETE FROM ordenador WHERE codord = %s;"
                cursor.execute(consulta, (codord))

                # No olvidemos hacer commit cuando hacemos un cambio a la BD
                self._conexion.commit()
                self.cerrarConexion()
                return cursor.rowcount #Registros afectados en el borrado.
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return -1


            #https://pynative.com/python-mysql-update-data/
    def modificarProfesor(self, codigo, nya, rol):
        """Insertar una persona en la tabla Personas."""
        try:
            self.conectar()
            cursor =  self._conexion.cursor()
            consulta = "UPDATE profesor SET codigo = %s, nya = %s, rol = %s WHERE codigo = %s;"
            cursor.execute(consulta, (codigo, nya, rol, codigo))
            self._conexion.commit()
            self.cerrarConexion()
            return cursor.rowcount
        except (pymysql.err.IntegrityError) as e:
            # print("Ocurrió un error al insertar: clave duplicada.", e)
            return -1

    def modificarAula(self, codaula, nomaula):
        """Insertar una persona en la tabla Personas."""
        try:
            self.conectar()
            cursor =  self._conexion.cursor()
            consulta = "UPDATE aula SET codaula = %s, nomaula = %s WHERE codaula = %s;"
            cursor.execute(consulta, (codaula, nomaula, codaula))
            self._conexion.commit()
            self.cerrarConexion()
            return cursor.rowcount
        except (pymysql.err.IntegrityError) as e:
            # print("Ocurrió un error al insertar: clave duplicada.", e)
            return -1

    def modificarOrdenador(self, codord, cpu, ram, almacenamiento, aula):
        """Insertar una persona en la tabla Personas."""
        try:
            self.conectar()
            cursor =  self._conexion.cursor()
            consulta = "UPDATE ordenador SET codord = %s, cpu = %s, ram = %s, almacenamiento = %s, aula = %s WHERE codord = %s;"
            cursor.execute(consulta, (codord, cpu, ram, almacenamiento, aula, codord))
            self._conexion.commit()
            self.cerrarConexion()
            return cursor.rowcount
        except (pymysql.err.IntegrityError) as e:
            # print("Ocurrió un error al insertar: clave duplicada.", e)
            return -1