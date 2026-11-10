export default async function handler(req, res) {
  // Configuración de CORS
  res.setHeader('Access-Control-Allow-Credentials', 'true');
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'GET,OPTIONS,POST');
  res.setHeader(
    'Access-Control-Allow-Headers',
    'X-CSRF-Token, X-Requested-With, Accept, Accept-Version, Content-Length, Content-MD5, Content-Type, Date, X-Api-Version'
  );

  if (req.method === 'OPTIONS') {
    return res.status(200).end();
  }

  if (req.method !== 'POST') {
    return res.status(405).json({ error: 'Method not allowed' });
  }

  const apiKey = process.env.GEMINI_API_KEY;
  if (!apiKey) {
    return res.status(400).json({
      error: 'GEMINI_API_KEY no configurada en las Variables de Entorno de Vercel.',
      code: 'API_KEY_MISSING'
    });
  }

  const { contents, systemInstruction, generationConfig } = req.body || {};
  if (!contents) {
    return res.status(400).json({ error: 'Cuerpo de petición incompleto (requiere contents).' });
  }

  const models = [
    'gemini-2.0-flash',
    'gemini-1.5-flash',
    'gemini-1.5-flash-8b'
  ];

  let lastError = null;

  for (const model of models) {
    try {
      const url = `https://generativelanguage.googleapis.com/v1beta/models/${model}:generateContent?key=${apiKey}`;
      const payload = {
        contents,
        generationConfig: generationConfig || { temperature: 0.4, maxOutputTokens: 900 }
      };

      if (systemInstruction) {
        payload.systemInstruction = { parts: [{ text: systemInstruction }] };
      }

      const response = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      });

      if (response.ok) {
        const data = await response.json();
        const text = data?.candidates?.[0]?.content?.parts?.[0]?.text;
        if (text && text.trim().length > 0) {
          return res.status(200).json({
            text: text.trim(),
            model,
            source: 'IA Gemini (Vercel API)'
          });
        }
      } else {
        const errorData = await response.text();
        lastError = `Status ${response.status}: ${errorData}`;
      }
    } catch (err) {
      lastError = err.message || err.toString();
    }
  }

  return res.status(502).json({
    error: 'No se pudo obtener respuesta de Gemini.',
    details: lastError
  });
}
