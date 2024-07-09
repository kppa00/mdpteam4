import mysql.connector

def query(query):
    conn = mysql.connector.connect(user='root', password='ai@factory', host='localhost', database='aifactory')
    cursor = conn.cursor()

    cursor.execute(query)
    if ("SELECT" in query):
        rows = cursor.fetchall()
    conn.commit()

    cursor.close()
    conn.close()
    
    if ("SELECT" in query):
        return rows