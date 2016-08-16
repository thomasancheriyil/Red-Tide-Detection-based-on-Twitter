from datetime import datetime
from lxml import  html, etree
import json, os, pytz, time

def pad(num, size):
    s = str(num)
    while len(s) < size:
        s = '0' + s
    return s

def ensureExists(directory):
    if not os.path.exists(directory):
        os.makedirs(directory)

def getPath(dt, keyword):
    dirYear = './twitter_' + keyword + '_' + str(dt.year)
    ensureExists(dirYear)
    dirMonth = os.path.join(dirYear, pad(dt.month, 2))
    ensureExists(dirMonth)
    #dirDay = os.path.join(dirMonth, pad(dt.day, 2))
    #ensureExists(dirDay)
    #dirHour = os.path.join(dirDay, pad(dt.hour, 2))
    #ensureExists(dirHour)
    fileMin = os.path.join(dirMonth, pad(dt.month, 2) + '.json')
    return fileMin

def checkIfExists(path, itemId):
    exists = False
    if os.path.isfile(path):
        with open(path, 'r') as f:
            for line in f:
                item = json.loads(line)
                if item['id_str'] == itemId:
                    exists = True
                    break
    return exists

def storeItem(item, keyword, lang):
    createdTime = datetime.fromtimestamp(long(item['timestamp_ms'])/1000, pytz.utc)
    path = getPath(createdTime, keyword)
    del item['timestamp_ms']
    stored = 0
    with open(path, 'a') as f:
        f.write('%s\n' % json.dumps(item))
    stored = 1
    return stored

def parse(start, end):
    fileName = 'test_%s_%s.html' % (start, end)
    with open(fileName, 'r') as f:
        text = f.read()
    tree = html.fromstring(text)
    cls = tree.xpath('.//div[@class="stream-item-header"]')
    print 'len(cls) = %s' % len(cls)
#     tweets = []
    for elem in cls:
        parent = elem.getparent()
        block = etree.tostring(parent)
        user_id = block.split('data-user-id="')[1].split('"')[0]
        username = block.split('<s>@</s><b>')[1].split('<')[0]
        status_id = block.split('/status/')[1].split('"')[0]
        ts = block.split('data-time-ms="')[1].split('"')[0]
        dt = datetime.fromtimestamp(long(ts)/1000, pytz.utc)
        created_at = dt.strftime('%a %b %d %H:%M:%S %z %Y')
        text = parent[1].text_content()
        item = {'id_str': status_id, 'text': text, 'timestamp_ms': ts, 'stream_type': 'Twitter'}
        storeItem(item, 'redtide', 'en')

def parse1(path):
    for fileName in os.listdir(path):
        unprocessed = 0
        with open(os.path.join(path, fileName), 'r') as f:
            text = f.read()
            tree = html.fromstring(text)
            cls = tree.xpath('.//div[@class="stream-item-header"]')
            print 'len(cls) = %s' % len(cls)
        #     tweets = []
            for elem in cls:
                parent = elem.getparent()
                block = etree.tostring(parent)
                user_id = block.split('data-user-id="')[1].split('"')[0]
                if '<s>@</s><b>' in block:
                    username = block.split('<s>@</s><b>')[1].split('<')[0]
                else:
                    unprocessed += 1
                    continue
                status_id = block.split('/status/')[1].split('"')[0]
                ts = block.split('data-time-ms="')[1].split('"')[0]
                dt = datetime.fromtimestamp(long(ts)/1000, pytz.utc)
                created_at = dt.strftime('%a %b %d %H:%M:%S %z %Y')
                text = parent[1].text_content()
                item = {'id_str': status_id, 'text': text, 'timestamp_ms': ts, 'stream_type': 'Twitter'}
                storeItem(item, 'redtide', 'en')
        if unprocessed>0:
            with open('TwitterGap.out', 'a') as f:
                f.write('file: %s, unprocessed: %s\n' % (fileName, unprocessed))

if __name__ == '__main__':
    print datetime.today()
    start = time.time()

#     parse('2014-10-04', '2014-10-07')
#     parse1('C:\\Users\\Bruiser3\\Documents\\grait-dm\\workspace\\grait-dm\\src\\TwitterGap')
    parse1('C:\\Users\\ThomasGeorge\\Documents\\python projects')

    end = time.time()
    print end - start
