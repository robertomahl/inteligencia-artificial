# Classe estado
class Estado:
    # Atributos
    # Matriz
    matriz = None
    # Pai
    pai = None
    # Distancia
    distancia = None
    # Numero de acoes
    numeroacoes = None

    # Construtor
    def __init__(self, matriz):
        self.matriz = matriz

    # Set numero de acoes
    def setNumeroAcoes(self, numeroacoes):
        self.numeroacoes = numeroacoes

    # Printa o estado
    def printa_estado(self, numeroestado):
        print(f"Estado {numeroestado}: Distancia {self.distancia} Numero de acoes {self.numeroacoes}")
        # Printa a matriz formatada
        for row in self.matriz:
            for item in row:
                print(" " if item == 'b' else item, end=" ")
            print("")


    # Verifica se o estado é igual
    def __eq__(self, other):
        # Compara as matrizes diretamente
        return all(self.matriz[i][j] == other.matriz[i][j] for i in range(3) for j in range(3))

    # adiciona o pai
    def addPai(self, pai):
        self.pai = pai

    def estados_sucessores(self):
        # Lista de estados
        estados = []
        # Acha a posição do b
        for i in range(3):
            for j in range(3):
                if self.matriz[i][j] == 'b':
                    posicaob = (i, j)
                    break

        # Define as operações para trocar o 'b' de posição
        operacoes = [(0, -1), (0, 1), (-1, 0), (1, 0)]  # (dx, dy) para esquerda, direita, cima, baixo

        # Para cada operação, verifica se é possível realizar a troca e gera o novo estado
        for dx, dy in operacoes:
            nova_posicaob = (posicaob[0] + dx, posicaob[1] + dy)
            if 0 <= nova_posicaob[0] < 3 and 0 <= nova_posicaob[1] < 3:
                matriz = [row.copy() for row in self.matriz]
                matriz[posicaob[0]][posicaob[1]], matriz[nova_posicaob[0]][nova_posicaob[1]] = matriz[nova_posicaob[0]][nova_posicaob[1]], 'b'
                estados.append(Estado(matriz))

        return estados

    # Pega heuristica
    def getHeuristica(self, estado_final):
        # Dicionário para mapear a posição de cada número na matriz final
        posicoes_numero_final = {estado_final.matriz[i][j]: (i, j) for i in range(3) for j in range(3)}

        # Calcula a distância Manhattan entre os números na matriz atual e na matriz final
        self.distancia = sum(abs(i - posicoes_numero_final[num][0]) + abs(j - posicoes_numero_final[num][1])
                            for i, row in enumerate(self.matriz)
                            for j, num in enumerate(row) if num != 'b')

# Funcao de busca considerando a heuristica
def busca_a_star(estado_inicial, estado_final):
    # Lista de estados abertos
    estados_abertos = []

    # Lista de estados fechados
    estados_fechados = []

    # Abre o estado inicial
    estados_abertos.append(estado_inicial)

    # Estado pos
    estado_pos = 1

    while estados_abertos:
        # Ordena a lista de estados abertos
        estados_abertos.sort(key=lambda x: x.distancia + x.numeroacoes)

        # Pega o primeiro estado
        estado_atual = estados_abertos.pop(0)

        # Printa o estado atual
        estado_atual.printa_estado(estado_pos)
        estado_pos += 1

        # Verifica se é o estado final
        if estado_atual == estado_final:
            return estado_atual

        # Abre o estado atual
        possiveis_estados = estado_atual.estados_sucessores()

        print(f"Estado escolhido: {estado_atual.matriz}")
        print("Possibilidades:")
        for estado in possiveis_estados:
            print(estado.matriz)

        # Para cada estado sucessor verificar se já foi visitado ou se já está na lista de estados abertos
        for estado in possiveis_estados:
            if estado not in estados_abertos and estado not in estados_fechados:
                # Adiciona o pai
                estado.addPai(estado_atual)
                # Adiciona o estado sucessor
                estados_abertos.append(estado)
                # Calcula a heuristica
                estado.getHeuristica(estado_final)
                # Set numero de acoes
                estado.setNumeroAcoes(estado_atual.numeroacoes + 1)

        # Fecha o estado atual
        estados_fechados.append(estado_atual)

    # Nao achou o estado final
    return None

# Funcao de busca gulosa considerando a heuristica
def busca_gulosa(estado_inicial, estado_final):
    # Lista de estados abertos
    estados_abertos = []

    # Lista de estados fechados
    estados_fechados = []

    # Abre o estado inicial
    estados_abertos.append(estado_inicial)

    # Estado pos
    estado_pos = 1

    while estados_abertos:
        # Ordena a lista de estados abertos pela heurística
        estados_abertos.sort(key=lambda x: x.distancia)

        # Pega o primeiro estado
        estado_atual = estados_abertos.pop(0)

        # Printa o estado atual
        estado_atual.printa_estado(estado_pos)
        estado_pos += 1

        # Verifica se é o estado final
        if estado_atual == estado_final:
            return estado_atual

        # Abre o estado atual
        possiveis_estados = estado_atual.estados_sucessores()

        print(f"Estado escolhido: {estado_atual.matriz}")
        print("Possibilidades:")
        for estado in possiveis_estados:
            print(estado.matriz)

        # Para cada estado sucessor verificar se já foi visitado ou se já está na lista de estados abertos
        for estado in possiveis_estados:
            if estado not in estados_abertos and estado not in estados_fechados:
                # Adiciona o pai
                estado.addPai(estado_atual)
                # Adiciona o estado sucessor
                estados_abertos.append(estado)
                # Calcula a heuristica
                estado.getHeuristica(estado_final)
                # Set numero de acoes
                estado.setNumeroAcoes(estado_atual.numeroacoes + 1)

        # Fecha o estado atual
        estados_fechados.append(estado_atual)

    # Nao achou o estado final
    return None

# Funcao de busca gulosa sem heuristica
def busca_gulosa_sem_heuristica(estado_inicial, estado_final):
    # Lista de estados abertos
    estados_abertos = []

    # Lista de estados fechados
    estados_fechados = []

    # Abre o estado inicial
    estados_abertos.append(estado_inicial)

    # Estado pos
    estado_pos = 1

    while estados_abertos:
        # Ordena a lista de estados abertos pela distância até o estado final
        estados_abertos.sort(key=lambda x: x.distancia if x.distancia is not None else float('inf'))

        # Pega o primeiro estado
        estado_atual = estados_abertos.pop(0)

        # Printa o estado atual
        estado_atual.printa_estado(estado_pos)
        estado_pos += 1

        # Verifica se é o estado final
        if estado_atual == estado_final:
            return estado_atual

        # Abre o estado atual
        possiveis_estados = estado_atual.estados_sucessores()

        print(f"Estado escolhido: {estado_atual.matriz}")
        print("Possibilidades:")
        for estado in possiveis_estados:
            print(estado.matriz)

        # Para cada estado sucessor, verificar se já foi visitado
        for estado in possiveis_estados:
            if estado not in estados_abertos and estado not in estados_fechados:
                # Adiciona o pai
                estado.addPai(estado_atual)
                # Adiciona o estado sucessor
                estados_abertos.append(estado)
                # Calcula a distância
                estado.getHeuristica(estado_final)
                # Set numero de acoes
                estado.setNumeroAcoes(estado_atual.numeroacoes + 1)

        # Fecha o estado atual
        estados_fechados.append(estado_atual)

    # Nao achou o estado final
    return None

estado_final = Estado([[1, 2, 3], [8, 'b', 4], [7, 6, 5]])
estado_inicial = Estado([[1, 3, 4], [8, 2, 5], [7, 6, 'b']])
estado_inicial2 = Estado([[1, 2, 3], ['b', 6, 4], [8, 7, 5]])

print("Estado inicial")
estado_inicial.getHeuristica(estado_final)
estado_inicial.setNumeroAcoes(0)
estado_inicial.printa_estado(0)

# Busca A*
print("------------------  Busca A* ------------------")
estado_final = busca_a_star(estado_inicial, estado_final)

# Busca gulosa
print("------------------  Busca Gulosa ------------------")
estado_final = busca_gulosa(estado_inicial, estado_final)

print("------------------  Busca Gulosa Sem Heuristica ------------------")
estado_final = busca_gulosa_sem_heuristica(estado_inicial, estado_final)

print("Estado inicial 2")
estado_inicial2.getHeuristica(estado_final)
estado_inicial2.setNumeroAcoes(0)
estado_inicial2.printa_estado(0)

# Busca A*
print("------------------  Busca A* ------------------")
estado_final = busca_a_star(estado_inicial2, estado_final)

# Busca gulosa
print("------------------  Busca Gulosa ------------------")
estado_final = busca_gulosa(estado_inicial2, estado_final)

print("------------------  Busca Gulosa Sem Heuristica ------------------")
estado_final = busca_gulosa_sem_heuristica(estado_inicial2, estado_final)

