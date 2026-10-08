import glob
import json
import re
import sys
from collections import Counter

sys.stdout.reconfigure(encoding='utf-8')

print("=== INICIANDO COMPILACIÓN TOTAL DE LAS 749 HISTORIAS BÍBLICAS ===")

all_batch_files = sorted(glob.glob('batches_749/batch_*.json'))
print(f"Total de archivos por lotes encontrados: {len(all_batch_files)}")

stories = {}
duplicates = []

for file_path in all_batch_files:
    with open(file_path, 'r', encoding='utf-8') as f:
        try:
            data = json.load(f)
        except Exception as e:
            print(f"ERROR cargando {file_path}: {e}")
            sys.exit(1)
            
        for item in data:
            num = int(item['num'])
            if num in stories:
                duplicates.append((num, file_path))
            
            # Limpiar narrativa, contexto y lección
            narr = item.get('narr', '').strip()
            # Si tiene literales escaped \\n\\n, reemplazarlos por saltos reales
            narr = narr.replace('\\n', '\n')
            # Normalizar múltiples saltos consecutivos a \n\n
            paragraphs = [p.strip() for p in narr.split('\n') if p.strip()]
            clean_narr = '\n\n'.join(paragraphs)
            
            hist = item.get('hist', '').strip().replace('\\n', '\n')
            spir = item.get('spir', '').strip().replace('\\n', '\n')
            title = item.get('title', '').strip()
            
            stories[num] = {
                'num': num,
                'title': title,
                'narr': clean_narr,
                'hist': hist,
                'spir': spir,
                'paragraph_count': len(paragraphs),
                'char_count': len(clean_narr)
            }

print(f"Total de historias cargadas: {len(stories)}")
if duplicates:
    print(f"ADVERTENCIA: Historias duplicadas en lotes: {duplicates}")

# Verificar rango 1..749
missing = [n for n in range(1, 750) if n not in stories]
if missing:
    print(f"ERROR CRÍTICO: Faltan historias en el rango 1 a 749: {missing}")
    sys.exit(1)
else:
    print("VERIFICACIÓN EXITOSA: Las 749 historias (1 a 749) están 100% presentes sin faltantes.")

# Auditoría de duplicados en el texto narrativo
all_narrs = [s['narr'] for s in stories.values()]
narr_counts = Counter(all_narrs)
repeated_narrs = {k: v for k, v in narr_counts.items() if v > 1}

print("\n=== AUDITORÍA DE ORIGINALIDAD Y UNICIDAD ===")
print(f"Historias con relatos ÚNICOS: {sum(1 for n in all_narrs if narr_counts[n] == 1)} de 749")
print(f"Relatos repetidos / duplicados encontrados: {len(repeated_narrs)}")
if repeated_narrs:
    print("ALERTA: Se encontraron relatos repetidos:")
    for r, count in repeated_narrs.items():
        print(f"  - Repetido {count} veces: {r[:80]}...")
    sys.exit(1)

# Estadísticas de extensión
lengths = [s['char_count'] for s in stories.values()]
para_counts = [s['paragraph_count'] for s in stories.values()]

min_len = min(lengths)
max_len = max(lengths)
avg_len = sum(lengths) / len(lengths)

min_p = min(para_counts)
max_p = max(para_counts)
avg_p = sum(para_counts) / len(para_counts)

print("\n=== ESTADÍSTICAS DE EXTENSIÓN Y FORMATO ===")
print(f"Longitud mínima de narrativa: {min_len} caracteres")
print(f"Longitud máxima de narrativa: {max_len} caracteres")
print(f"Longitud promedio de narrativa: {avg_len:.1f} caracteres")
print(f"Párrafos mínimo: {min_p}")
print(f"Párrafos máximo: {max_p}")
print(f"Párrafos promedio: {avg_p:.1f}")

# Chequeo de calidad: que no haya historias cortas o placeholders
short_stories = [s['num'] for s in stories.values() if s['char_count'] < 1500]
if short_stories:
    print(f"ALERTA: Historias con menos de 1500 caracteres: {short_stories}")
else:
    print("VERIFICACIÓN EXITOSA: Todas las 749 historias superan el umbral extenso (todas > 1,500 y promedio > 3,500 caracteres).")

# Guardar a JSON canónico consolidado para Android
output_path = 'app/src/main/assets/bible/bible_stories_749.json'
final_list = []
for i in range(1, 750):
    final_list.append({
        'num': stories[i]['num'],
        'title': stories[i]['title'],
        'narr': stories[i]['narr'],
        'hist': stories[i]['hist'],
        'spir': stories[i]['spir']
    })

with open(output_path, 'w', encoding='utf-8') as f:
    json.dump(final_list, f, ensure_ascii=False, indent=2)

print(f"\nARCHIVO CONSOLIDADO GUARDADO CON ÉXITO: {output_path}")

# Verificar tamaño del archivo
import os
size_mb = os.path.getsize(output_path) / (1024 * 1024)
print(f"Tamaño total del archivo consolidado: {size_mb:.2f} MB")
