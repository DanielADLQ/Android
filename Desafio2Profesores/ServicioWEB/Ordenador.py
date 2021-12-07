import json

class OrdenadorEncoder(json.JSONEncoder):

    def default(self, obj):
        return obj.__dict__


class Ordenador(object):
    def __init__(self, codord, cpu, ram, almacenamiento, aula) -> None :
        self.codord = codord
        self.cpu = cpu
        self.ram = ram
        self.almacenamiento = almacenamiento
        self.aula = aula

    # def toJSON(self):
    #     #return json.dumps(self, default=lambda o: o.__dict__, sort_keys=True, indent=4)
    #     return json.dump(self)
    #     #return json.dumps(self, skipkeys=False, ensure_ascii=True, check_circular=True, allow_nan=True, cls=None, indent=None, separators=None, default=None, sort_keys=False, **kw)

    def __str__(self):
        return json.dumps(self)

#http://daniel.blogmatico.com/convertir-un-objeto-python-a-un-objeto-json/