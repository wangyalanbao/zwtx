/*global exports */
/*!
 * emoji
 *
 * This file auto create by `bin/create_emoji_js.py`.
 * Emoji\'s table come from <a href="http://code.iamcal.com/php/emoji/">http://code.iamcal.com/php/emoji/</a>
 *
 * Copyright(c) 2012 - 2014 fengmk2 <fengmk2@gmail.com>
 * MIT Licensed
 */

;(function (name, definition) {
  // Come from eventproxy: https://github.com/JacksonTian/eventproxy/blob/master/lib/eventproxy.js#L7

  // this is considered "safe":
  var hasDefine = typeof define === 'function';
  var hasExports = typeof module !== 'undefined' && module.exports;

  if (hasDefine) {
    // AMD Module or CMD Module
    define(definition);
  } else if (hasExports) {
    // Node.js Module
    module.exports = definition();
  } else {
    // Assign to common namespaces or simply the global object (window)
    this[name] = definition();
  }
})('jEmoji', function () {

var jEmoji = {
  EMOJI_RE: function () {
    return EMOJI_RE
      || (EMOJI_RE = _createRegexp(EMOJI_MAP));
  },
  EMOJI_DOCOMO_RE: function () {
    return EMOJI_DOCOMO_RE
      || (EMOJI_DOCOMO_RE = _createRegexp(EMOJI_DOCOMO_MAP));
  },
  EMOJI_KDDI_RE: function () {
    return EMOJI_KDDI_RE
      || (EMOJI_KDDI_RE = _createRegexp(EMOJI_KDDI_MAP));
  },
  EMOJI_SOFTBANK_RE: function () {
    return EMOJI_SOFTBANK_RE
      || (EMOJI_SOFTBANK_RE = _createRegexp(EMOJI_SOFTBANK_MAP));
  },
  EMOJI_GOOGLE_RE: function () {
    return EMOJI_GOOGLE_RE
      || (EMOJI_GOOGLE_RE = _createRegexp(EMOJI_GOOGLE_MAP));
  }
};

/**
 * Emoji code map.
 *
 * format:
 *   Unified: [unified_unicode, title, classname, DoCoMo, KDDI, Softbank, Google]'
 *
 * @type {Object}
 */
var EMOJI_MAP = jEmoji.EMOJI_MAP = {
  // table.html
  "😲": ["U+1F632", "astonished face", "1f632", ["", "U+E6F4"], ["", "U+EACA"], ["", "U+E410"], ["󾌢", "U+FE322"]],
  "😞": ["U+1F61E", "disappointed face", "1f61e", ["", "U+E6F2"], ["", "U+EAC0"], ["", "U+E058"], ["󾌣", "U+FE323"]],
  "😰": ["U+1F630", "face with open mouth and cold sweat", "1f630", ["", "U+E723"], ["", "U+EACB"], ["", "U+E40F"], ["󾌥", "U+FE325"]],
  "😒": ["U+1F612", "unamused face", "1f612", ["", "U+E725"], ["", "U+EAC9"], ["", "U+E40E"], ["󾌦", "U+FE326"]],
  "😍": ["U+1F60D", "smiling face with heart-shaped eyes", "1f60d", ["", "U+E726"], ["", "U+E5C4"], ["", "U+E106"], ["󾌧", "U+FE327"]],
  "😜": ["U+1F61C", "face with stuck-out tongue and winking eye", "1f61c", ["", "U+E728"], ["", "U+E4E7"], ["", "U+E105"], ["󾌩", "U+FE329"]],
  "😝": ["U+1F61D", "face with stuck-out tongue and tightly-closed eyes", "1f61d", ["", "U+E728"], ["", "U+E4E7"], ["", "U+E409"], ["󾌪", "U+FE32A"]],
  "😘": ["U+1F618", "face throwing a kiss", "1f618", ["", "U+E726"], ["", "U+EACF"], ["", "U+E418"], ["󾌬", "U+FE32C"]],
  "😚": ["U+1F61A", "kissing face with closed eyes", "1f61a", ["", "U+E726"], ["", "U+EACE"], ["", "U+E417"], ["󾌭", "U+FE32D"]],
  "😷": ["U+1F637", "face with medical mask", "1f637", ["-", "-"], ["", "U+EAC7"], ["", "U+E40C"], ["󾌮", "U+FE32E"]],
  "😳": ["U+1F633", "flushed face", "1f633", ["", "U+E72A"], ["", "U+EAC8"], ["", "U+E40D"], ["󾌯", "U+FE32F"]],
  "😃": ["U+1F603", "smiling face with open mouth", "1f603", ["", "U+E6F0"], ["", "U+E471"], ["", "U+E057"], ["󾌰", "U+FE330"]],
  "😁": ["U+1F601", "grinning face with smiling eyes", "1f601", ["", "U+E753"], ["", "U+EB80"], ["", "U+E404"], ["󾌳", "U+FE333"]],
  "😂": ["U+1F602", "face with tears of joy", "1f602", ["", "U+E72A"], ["", "U+EB64"], ["", "U+E412"], ["󾌴", "U+FE334"]],
  "😊": ["U+1F60A", "smiling face with smiling eyes", "1f60a", ["", "U+E6F0"], ["", "U+EACD"], ["", "U+E056"], ["󾌵", "U+FE335"]],
  "☺": ["U+263A", "white smiling face", "263a", ["", "U+E6F0"], ["", "U+E4FB"], ["", "U+E414"], ["󾌶", "U+FE336"]],
  "😄": ["U+1F604", "smiling face with open mouth and smiling eyes", "1f604", ["", "U+E6F0"], ["", "U+E471"], ["", "U+E415"], ["󾌸", "U+FE338"]],
  "😢": ["U+1F622", "crying face", "1f622", ["", "U+E72E"], ["", "U+EB69"], ["", "U+E413"], ["󾌹", "U+FE339"]],
  "😭": ["U+1F62D", "loudly crying face", "1f62d", ["", "U+E72D"], ["", "U+E473"], ["", "U+E411"], ["󾌺", "U+FE33A"]],
  "😨": ["U+1F628", "fearful face", "1f628", ["", "U+E757"], ["", "U+EAC6"], ["", "U+E40B"], ["󾌻", "U+FE33B"]],
  "😣": ["U+1F623", "persevering face", "1f623", ["", "U+E72B"], ["", "U+EAC2"], ["", "U+E406"], ["󾌼", "U+FE33C"]],
  "😡": ["U+1F621", "pouting face", "1f621", ["", "U+E724"], ["", "U+EB5D"], ["", "U+E416"], ["󾌽", "U+FE33D"]],
  "😌": ["U+1F60C", "relieved face", "1f60c", ["", "U+E721"], ["", "U+EAC5"], ["", "U+E40A"], ["󾌾", "U+FE33E"]],
  "😖": ["U+1F616", "confounded face", "1f616", ["", "U+E6F3"], ["", "U+EAC3"], ["", "U+E407"], ["󾌿", "U+FE33F"]],
  "😔": ["U+1F614", "pensive face", "1f614", ["", "U+E720"], ["", "U+EAC0"], ["", "U+E403"], ["󾍀", "U+FE340"]],
  "😱": ["U+1F631", "face screaming in fear", "1f631", ["", "U+E757"], ["", "U+E5C5"], ["", "U+E107"], ["󾍁", "U+FE341"]],
  "😪": ["U+1F62A", "sleepy face", "1f62a", ["", "U+E701"], ["", "U+EAC4"], ["", "U+E408"], ["󾍂", "U+FE342"]],
  "😏": ["U+1F60F", "smirking face", "1f60f", ["", "U+E72C"], ["", "U+EABF"], ["", "U+E402"], ["󾍃", "U+FE343"]],
  "😓": ["U+1F613", "face with cold sweat", "1f613", ["", "U+E723"], ["", "U+E5C6"], ["", "U+E108"], ["󾍄", "U+FE344"]],
  "😥": ["U+1F625", "disappointed but relieved face", "1f625", ["", "U+E723"], ["", "U+E5C6"], ["", "U+E401"], ["󾍅", "U+FE345"]],  
  "😉": ["U+1F609", "winking face", "1f609", ["", "U+E729"], ["", "U+E5C3"], ["", "U+E405"], ["󾍇", "U+FE347"]],
  "🙅": ["U+1F645", "face with no good gesture", "1f645", ["", "U+E72F"], ["", "U+EAD7"], ["", "U+E423"], ["󾍑", "U+FE351"]],
  "🙆": ["U+1F646", "face with ok gesture", "1f646", ["", "U+E70B"], ["", "U+EAD8"], ["", "U+E424"], ["󾍒", "U+FE352"]],
  "🙇": ["U+1F647", "person bowing deeply", "1f647", ["-", "-"], ["", "U+EAD9"], ["", "U+E426"], ["󾍓", "U+FE353"]],
  "🙈": ["U+1F648", "see-no-evil monkey", "1f648", ["-", "-"], ["", "U+EB50"], ["-", "-"], ["󾍔", "U+FE354"]],  
  "🙌": ["U+1F64C", "person raising both hands in celebration", "1f64c", ["-", "-"], ["", "U+EB86"], ["", "U+E427"], ["󾍘", "U+FE358"]],
  "🙏": ["U+1F64F", "person with folded hands", "1f64f", ["-", "-"], ["", "U+EAD2"], ["", "U+E41D"], ["󾍛", "U+FE35B"]],
  "🏠": ["U+1F3E0", "house building", "1f3e0", ["", "U+E663"], ["", "U+E4AB"], ["", "U+E036"], ["󾒰", "U+FE4B0"]],
  "🏢": ["U+1F3E2", "office building", "1f3e2", ["", "U+E664"], ["", "U+E4AD"], ["", "U+E038"], ["󾒲", "U+FE4B2"]],
  "🏣": ["U+1F3E3", "japanese post office", "1f3e3", ["", "U+E665"], ["", "U+E5DE"], ["", "U+E153"], ["󾒳", "U+FE4B3"]],
  "🏥": ["U+1F3E5", "hospital", "1f3e5", ["", "U+E666"], ["", "U+E5DF"], ["", "U+E155"], ["󾒴", "U+FE4B4"]],
  "🏦": ["U+1F3E6", "bank", "1f3e6", ["", "U+E667"], ["", "U+E4AA"], ["", "U+E14D"], ["󾒵", "U+FE4B5"]],
  "🏧": ["U+1F3E7", "automated teller machine", "1f3e7", ["", "U+E668"], ["", "U+E4A3"], ["", "U+E154"], ["󾒶", "U+FE4B6"]],
  "🏨": ["U+1F3E8", "hotel", "1f3e8", ["", "U+E669"], ["", "U+EA81"], ["", "U+E158"], ["󾒷", "U+FE4B7"]],
  "🏩": ["U+1F3E9", "love hotel", "1f3e9", ["", "U+E669 U+E6EF"], ["", "U+EAF3"], ["", "U+E501"], ["󾒸", "U+FE4B8"]],
  "🏪": ["U+1F3EA", "convenience store", "1f3ea", ["", "U+E66A"], ["", "U+E4A4"], ["", "U+E156"], ["󾒹", "U+FE4B9"]],
  "🏫": ["U+1F3EB", "school", "1f3eb", ["", "U+E73E"], ["", "U+EA80"], ["", "U+E157"], ["󾒺", "U+FE4BA"]],
  "⛪": ["U+26EA", "church", "26ea", ["-", "-"], ["", "U+E5BB"], ["", "U+E037"], ["󾒻", "U+FE4BB"]],
  "⛲": ["U+26F2", "fountain", "26f2", ["-", "-"], ["", "U+E5CF"], ["", "U+E121"], ["󾒼", "U+FE4BC"]],
  "🏬": ["U+1F3EC", "department store", "1f3ec", ["-", "-"], ["", "U+EAF6"], ["", "U+E504"], ["󾒽", "U+FE4BD"]],
  "🏯": ["U+1F3EF", "japanese castle", "1f3ef", ["-", "-"], ["", "U+EAF7"], ["", "U+E505"], ["󾒾", "U+FE4BE"]],
  "🏰": ["U+1F3F0", "european castle", "1f3f0", ["-", "-"], ["", "U+EAF8"], ["", "U+E506"], ["󾒿", "U+FE4BF"]],
  "🏭": ["U+1F3ED", "factory", "1f3ed", ["-", "-"], ["", "U+EAF9"], ["", "U+E508"], ["󾓀", "U+FE4C0"]],
  "🗻": ["U+1F5FB", "mount fuji", "1f5fb", ["", "U+E740"], ["", "U+E5BD"], ["", "U+E03B"], ["󾓃", "U+FE4C3"]],
  "🗼": ["U+1F5FC", "tokyo tower", "1f5fc", ["-", "-"], ["", "U+E4C0"], ["", "U+E509"], ["󾓄", "U+FE4C4"]],
  "🗽": ["U+1F5FD", "statue of liberty", "1f5fd", ["-", "-"], ["-", "-"], ["", "U+E51D"], ["󾓆", "U+FE4C6"]],
  "👟": ["U+1F45F", "athletic shoe", "1f45f", ["", "U+E699"], ["", "U+EB2B"], ["", "U+E007"], ["󾓍", "U+FE4CD"]],
  "👠": ["U+1F460", "high-heeled shoe", "1f460", ["", "U+E674"], ["", "U+E51A"], ["", "U+E13E"], ["󾓖", "U+FE4D6"]],
  "👡": ["U+1F461", "womans sandal", "1f461", ["", "U+E674"], ["", "U+E51A"], ["", "U+E31A"], ["󾓗", "U+FE4D7"]],
  "👢": ["U+1F462", "womans boots", "1f462", ["-", "-"], ["", "U+EA9F"], ["", "U+E31B"], ["󾓘", "U+FE4D8"]],
  "👣": ["U+1F463", "footprints", "1f463", ["", "U+E698"], ["", "U+EB2A"], ["", "U+E536"], ["󾕓", "U+FE553"]],
  "👕": ["U+1F455", "t-shirt", "1f455", ["", "U+E70E"], ["", "U+E5B6"], ["", "U+E006"], ["󾓏", "U+FE4CF"]],
  "👑": ["U+1F451", "crown", "1f451", ["", "U+E71A"], ["", "U+E5C9"], ["", "U+E10E"], ["󾓑", "U+FE4D1"]],
  "👔": ["U+1F454", "necktie", "1f454", ["-", "-"], ["", "U+EA93"], ["", "U+E302"], ["󾓓", "U+FE4D3"]],
  "👒": ["U+1F452", "womans hat", "1f452", ["-", "-"], ["", "U+EA9E"], ["", "U+E318"], ["󾓔", "U+FE4D4"]],
  "👗": ["U+1F457", "dress", "1f457", ["-", "-"], ["", "U+EB6B"], ["", "U+E319"], ["󾓕", "U+FE4D5"]],
  "👘": ["U+1F458", "kimono", "1f458", ["-", "-"], ["", "U+EAA3"], ["", "U+E321"], ["󾓙", "U+FE4D9"]],
  "👙": ["U+1F459", "bikini", "1f459", ["-", "-"], ["", "U+EAA4"], ["", "U+E322"], ["󾓚", "U+FE4DA"]],
  "👜": ["U+1F45C", "handbag", "1f45c", ["", "U+E682"], ["", "U+E49C"], ["", "U+E323"], ["󾓰", "U+FE4F0"]],
  "💰": ["U+1F4B0", "money bag", "1f4b0", ["", "U+E715"], ["", "U+E4C7"], ["", "U+E12F"], ["󾓝", "U+FE4DD"]],
  "💱": ["U+1F4B1", "currency exchange", "1f4b1", ["-", "-"], ["-", "-"], ["", "U+E149"], ["󾓞", "U+FE4DE"]],
  "💹": ["U+1F4B9", "chart with upwards trend and yen sign", "1f4b9", ["-", "-"], ["", "U+E5DC"], ["", "U+E14A"], ["󾓟", "U+FE4DF"]],
  "🇨🇳": ["U+1F1E8 U+1F1F3", "regional indicator symbol letters cn", "1f1e81f1f3", ["-", "-"], ["", "U+EB11"], ["", "U+E513"], ["󾓭", "U+FE4ED"]],
  "🇩🇪": ["U+1F1E9 U+1F1EA", "regional indicator symbol letters de", "1f1e91f1ea", ["-", "-"], ["", "U+EB0E"], ["", "U+E50E"], ["󾓨", "U+FE4E8"]],
  "🇪🇸": ["U+1F1EA U+1F1F8", "regional indicator symbol letters es", "1f1ea1f1f8", ["-", "-"], ["", "U+E5D5"], ["", "U+E511"], ["󾓫", "U+FE4EB"]],
  "🇫🇷": ["U+1F1EB U+1F1F7", "regional indicator symbol letters fr", "1f1eb1f1f7", ["-", "-"], ["", "U+EAFA"], ["", "U+E50D"], ["󾓧", "U+FE4E7"]],
  "🇬🇧": ["U+1F1EC U+1F1E7", "regional indicator symbol letters gb", "1f1ec1f1e7", ["-", "-"], ["", "U+EB10"], ["", "U+E510"], ["󾓪", "U+FE4EA"]],
  "🇮🇹": ["U+1F1EE U+1F1F9", "regional indicator symbol letters it", "1f1ee1f1f9", ["-", "-"], ["", "U+EB0F"], ["", "U+E50F"], ["󾓩", "U+FE4E9"]],
  "🇯🇵": ["U+1F1EF U+1F1F5", "regional indicator symbol letters jp", "1f1ef1f1f5", ["-", "-"], ["", "U+E4CC"], ["", "U+E50B"], ["󾓥", "U+FE4E5"]],
  "🇰🇷": ["U+1F1F0 U+1F1F7", "regional indicator symbol letters kr", "1f1f01f1f7", ["-", "-"], ["", "U+EB12"], ["", "U+E514"], ["󾓮", "U+FE4EE"]],
  "🇷🇺": ["U+1F1F7 U+1F1FA", "regional indicator symbol letters ru", "1f1f71f1fa", ["-", "-"], ["", "U+E5D6"], ["", "U+E512"], ["󾓬", "U+FE4EC"]],
  "🇺🇸": ["U+1F1FA U+1F1F8", "regional indicator symbol letters us", "1f1fa1f1f8", ["-", "-"], ["", "U+E573"], ["", "U+E50C"], ["󾓦", "U+FE4E6"]],
  "🔥": ["U+1F525", "fire", "1f525", ["-", "-"], ["", "U+E47B"], ["", "U+E11D"], ["󾓶", "U+FE4F6"]],
  "🔨": ["U+1F528", "hammer", "1f528", ["-", "-"], ["", "U+E5CB"], ["", "U+E116"], ["󾓊", "U+FE4CA"]],
  "🔫": ["U+1F52B", "pistol", "1f52b", ["-", "-"], ["", "U+E50A"], ["", "U+E113"], ["󾓵", "U+FE4F5"]],
  "🔯": ["U+1F52F", "six pointed star with middle dot", "1f52f", ["-", "-"], ["", "U+EA8F"], ["", "U+E23E"], ["󾓸", "U+FE4F8"]],
  "🔰": ["U+1F530", "japanese symbol for beginner", "1f530", ["-", "-"], ["", "U+E480"], ["", "U+E209"], ["󾁄", "U+FE044"]],
  "🔱": ["U+1F531", "trident emblem", "1f531", ["", "U+E71A"], ["", "U+E5C9"], ["", "U+E031"], ["󾓒", "U+FE4D2"]],
  "💉": ["U+1F489", "syringe", "1f489", ["-", "-"], ["", "U+E510"], ["", "U+E13B"], ["󾔉", "U+FE509"]],
  "💊": ["U+1F48A", "pill", "1f48a", ["-", "-"], ["", "U+EA9A"], ["", "U+E30F"], ["󾔊", "U+FE50A"]],
  "🅰": ["U+1F170", "negative squared latin capital letter a", "1f170", ["-", "-"], ["", "U+EB26"], ["", "U+E532"], ["󾔋", "U+FE50B"]],
  "🅱": ["U+1F171", "negative squared latin capital letter b", "1f171", ["-", "-"], ["", "U+EB27"], ["", "U+E533"], ["󾔌", "U+FE50C"]],
  "🆎": ["U+1F18E", "negative squared ab", "1f18e", ["-", "-"], ["", "U+EB29"], ["", "U+E534"], ["󾔍", "U+FE50D"]],
  "🅾": ["U+1F17E", "negative squared latin capital letter o", "1f17e", ["-", "-"], ["", "U+EB28"], ["", "U+E535"], ["󾔎", "U+FE50E"]],
  "🎀": ["U+1F380", "ribbon", "1f380", ["", "U+E684"], ["", "U+E59F"], ["", "U+E314"], ["󾔏", "U+FE50F"]],
  "🎁": ["U+1F381", "wrapped present", "1f381", ["", "U+E685"], ["", "U+E4CF"], ["", "U+E112"], ["󾔐", "U+FE510"]],
  "🎂": ["U+1F382", "birthday cake", "1f382", ["", "U+E686"], ["", "U+E5A0"], ["", "U+E34B"], ["󾔑", "U+FE511"]],
  "🎄": ["U+1F384", "christmas tree", "1f384", ["", "U+E6A4"], ["", "U+E4C9"], ["", "U+E033"], ["󾔒", "U+FE512"]],
  "🎅": ["U+1F385", "father christmas", "1f385", ["-", "-"], ["", "U+EAF0"], ["", "U+E448"], ["󾔓", "U+FE513"]],
  "🎌": ["U+1F38C", "crossed flags", "1f38c", ["-", "-"], ["", "U+E5D9"], ["", "U+E143"], ["󾔔", "U+FE514"]],
  "🎆": ["U+1F386", "fireworks", "1f386", ["-", "-"], ["", "U+E5CC"], ["", "U+E117"], ["󾔕", "U+FE515"]],
  "🎈": ["U+1F388", "balloon", "1f388", ["-", "-"], ["", "U+EA9B"], ["", "U+E310"], ["󾔖", "U+FE516"]],
  "🎉": ["U+1F389", "party popper", "1f389", ["-", "-"], ["", "U+EA9C"], ["", "U+E312"], ["󾔗", "U+FE517"]],
  "🎍": ["U+1F38D", "pine decoration", "1f38d", ["-", "-"], ["", "U+EAE3"], ["", "U+E436"], ["󾔘", "U+FE518"]],
  "🎎": ["U+1F38E", "japanese dolls", "1f38e", ["-", "-"], ["", "U+EAE4"], ["", "U+E438"], ["󾔙", "U+FE519"]],
  "🎓": ["U+1F393", "graduation cap", "1f393", ["-", "-"], ["", "U+EAE5"], ["", "U+E439"], ["󾔚", "U+FE51A"]],
  "🎒": ["U+1F392", "school satchel", "1f392", ["-", "-"], ["", "U+EAE6"], ["", "U+E43A"], ["󾔛", "U+FE51B"]],
  "🎏": ["U+1F38F", "carp streamer", "1f38f", ["-", "-"], ["", "U+EAE7"], ["", "U+E43B"], ["󾔜", "U+FE51C"]],
  "🎇": ["U+1F387", "firework sparkler", "1f387", ["-", "-"], ["", "U+EAEB"], ["", "U+E440"], ["󾔝", "U+FE51D"]],
  "🎐": ["U+1F390", "wind chime", "1f390", ["-", "-"], ["", "U+EAED"], ["", "U+E442"], ["󾔞", "U+FE51E"]],
  "🎃": ["U+1F383", "jack-o-lantern", "1f383", ["-", "-"], ["", "U+EAEE"], ["", "U+E445"], ["󾔟", "U+FE51F"]],
  "🎑": ["U+1F391", "moon viewing ceremony", "1f391", ["-", "-"], ["", "U+EAEF"], ["", "U+E446"], ["󾀗", "U+FE017"]],
  "☎": ["U+260E", "black telephone", "260e", ["", "U+E687"], ["", "U+E596"], ["", "U+E009"], ["󾔣", "U+FE523"]],
  "📱": ["U+1F4F1", "mobile phone", "1f4f1", ["", "U+E688"], ["", "U+E588"], ["", "U+E00A"], ["󾔥", "U+FE525"]],
  "📲": ["U+1F4F2", "mobile phone with rightwards arrow at left", "1f4f2", ["", "U+E6CE"], ["", "U+EB08"], ["", "U+E104"], ["󾔦", "U+FE526"]],
  "📝": ["U+1F4DD", "memo", "1f4dd", ["", "U+E689"], ["", "U+EA92"], ["", "U+E301"], ["󾔧", "U+FE527"]],
  "📠": ["U+1F4E0", "fax machine", "1f4e0", ["", "U+E6D0"], ["", "U+E520"], ["", "U+E00B"], ["󾔨", "U+FE528"]],
  "📩": ["U+1F4E9", "envelope with downwards arrow above", "1f4e9", ["", "U+E6CF"], ["", "U+EB62"], ["", "U+E103"], ["󾔫", "U+FE52B"]],
  "📫": ["U+1F4EB", "closed mailbox with raised flag", "1f4eb", ["", "U+E665"], ["", "U+EB0A"], ["", "U+E101"], ["󾔭", "U+FE52D"]],
  "📮": ["U+1F4EE", "postbox", "1f4ee", ["", "U+E665"], ["", "U+E51B"], ["", "U+E102"], ["󾔮", "U+FE52E"]],
  "📢": ["U+1F4E2", "public address loudspeaker", "1f4e2", ["-", "-"], ["", "U+E511"], ["", "U+E142"], ["󾔯", "U+FE52F"]],
  "📣": ["U+1F4E3", "cheering megaphone", "1f4e3", ["-", "-"], ["", "U+E511"], ["", "U+E317"], ["󾔰", "U+FE530"]],
  "📡": ["U+1F4E1", "satellite antenna", "1f4e1", ["-", "-"], ["", "U+E4A8"], ["", "U+E14B"], ["󾔱", "U+FE531"]],
  "💺": ["U+1F4BA", "seat", "1f4ba", ["", "U+E6B2"], ["-", "-"], ["", "U+E11F"], ["󾔷", "U+FE537"]],
  "💻": ["U+1F4BB", "personal computer", "1f4bb", ["", "U+E716"], ["", "U+E5B8"], ["", "U+E00C"], ["󾔸", "U+FE538"]],
  "💼": ["U+1F4BC", "briefcase", "1f4bc", ["", "U+E682"], ["", "U+E5CE"], ["", "U+E11E"], ["󾔻", "U+FE53B"]],
  "💽": ["U+1F4BD", "minidisc", "1f4bd", ["-", "-"], ["", "U+E582"], ["", "U+E316"], ["󾔼", "U+FE53C"]],
  "💾": ["U+1F4BE", "floppy disk", "1f4be", ["-", "-"], ["", "U+E562"], ["", "U+E316"], ["󾔽", "U+FE53D"]],
  "💿": ["U+1F4BF", "optical disc", "1f4bf", ["", "U+E68C"], ["", "U+E50C"], ["", "U+E126"], ["󾠝", "U+FE81D"]],
  "📀": ["U+1F4C0", "dvd", "1f4c0", ["", "U+E68C"], ["", "U+E50C"], ["", "U+E127"], ["󾠞", "U+FE81E"]],
  "✂": ["U+2702", "black scissors", "2702", ["", "U+E675"], ["", "U+E516"], ["", "U+E313"], ["󾔾", "U+FE53E"]],
  "📖": ["U+1F4D6", "open book", "1f4d6", ["", "U+E683"], ["", "U+E49F"], ["", "U+E148"], ["󾕆", "U+FE546"]],
  "⚾": ["U+26BE", "baseball", "26be", ["", "U+E653"], ["", "U+E4BA"], ["", "U+E016"], ["󾟑", "U+FE7D1"]],
  "⛳": ["U+26F3", "flag in hole", "26f3", ["", "U+E654"], ["", "U+E599"], ["", "U+E014"], ["󾟒", "U+FE7D2"]],
  "🎾": ["U+1F3BE", "tennis racquet and ball", "1f3be", ["", "U+E655"], ["", "U+E4B7"], ["", "U+E015"], ["󾟓", "U+FE7D3"]],
  "⚽": ["U+26BD", "soccer ball", "26bd", ["", "U+E656"], ["", "U+E4B6"], ["", "U+E018"], ["󾟔", "U+FE7D4"]],
  "🎿": ["U+1F3BF", "ski and ski boot", "1f3bf", ["", "U+E657"], ["", "U+EAAC"], ["", "U+E013"], ["󾟕", "U+FE7D5"]],
  "🏀": ["U+1F3C0", "basketball and hoop", "1f3c0", ["", "U+E658"], ["", "U+E59A"], ["", "U+E42A"], ["󾟖", "U+FE7D6"]],
  "🏁": ["U+1F3C1", "chequered flag", "1f3c1", ["", "U+E659"], ["", "U+E4B9"], ["", "U+E132"], ["󾟗", "U+FE7D7"]],
  "🏂": ["U+1F3C2", "snowboarder", "1f3c2", ["", "U+E712"], ["", "U+E4B8"], ["-", "-"], ["󾟘", "U+FE7D8"]],
  "🏃": ["U+1F3C3", "runner", "1f3c3", ["", "U+E733"], ["", "U+E46B"], ["", "U+E115"], ["󾟙", "U+FE7D9"]],
  "🏄": ["U+1F3C4", "surfer", "1f3c4", ["", "U+E712"], ["", "U+EB41"], ["", "U+E017"], ["󾟚", "U+FE7DA"]],
  "🏆": ["U+1F3C6", "trophy", "1f3c6", ["-", "-"], ["", "U+E5D3"], ["", "U+E131"], ["󾟛", "U+FE7DB"]],
  "🏈": ["U+1F3C8", "american football", "1f3c8", ["-", "-"], ["", "U+E4BB"], ["", "U+E42B"], ["󾟝", "U+FE7DD"]],
  "🏊": ["U+1F3CA", "swimmer", "1f3ca", ["-", "-"], ["", "U+EADE"], ["", "U+E42D"], ["󾟞", "U+FE7DE"]],
  "🚃": ["U+1F683", "railway car", "1f683", ["", "U+E65B"], ["", "U+E4B5"], ["", "U+E01E"], ["󾟟", "U+FE7DF"]],
  "🚇": ["U+1F687", "metro", "1f687", ["", "U+E65C"], ["", "U+E5BC"], ["", "U+E434"], ["󾟠", "U+FE7E0"]],
  "🚄": ["U+1F684", "high-speed train", "1f684", ["", "U+E65D"], ["", "U+E4B0"], ["", "U+E435"], ["󾟢", "U+FE7E2"]],
  "🚅": ["U+1F685", "high-speed train with bullet nose", "1f685", ["", "U+E65D"], ["", "U+E4B0"], ["", "U+E01F"], ["󾟣", "U+FE7E3"]],
  "🚗": ["U+1F697", "automobile", "1f697", ["", "U+E65E"], ["", "U+E4B1"], ["", "U+E01B"], ["󾟤", "U+FE7E4"]],
  "🚙": ["U+1F699", "recreational vehicle", "1f699", ["", "U+E65F"], ["", "U+E4B1"], ["", "U+E42E"], ["󾟥", "U+FE7E5"]],
  "🚌": ["U+1F68C", "bus", "1f68c", ["", "U+E660"], ["", "U+E4AF"], ["", "U+E159"], ["󾟦", "U+FE7E6"]],
  "🚏": ["U+1F68F", "bus stop", "1f68f", ["-", "-"], ["", "U+E4A7"], ["", "U+E150"], ["󾟧", "U+FE7E7"]],
  "🚢": ["U+1F6A2", "ship", "1f6a2", ["", "U+E661"], ["", "U+EA82"], ["", "U+E202"], ["󾟨", "U+FE7E8"]],
  "✈": ["U+2708", "airplane", "2708", ["", "U+E662"], ["", "U+E4B3"], ["", "U+E01D"], ["󾟩", "U+FE7E9"]],
  "⛵": ["U+26F5", "sailboat", "26f5", ["", "U+E6A3"], ["", "U+E4B4"], ["", "U+E01C"], ["󾟪", "U+FE7EA"]],
  "🚉": ["U+1F689", "station", "1f689", ["-", "-"], ["", "U+EB6D"], ["", "U+E039"], ["󾟬", "U+FE7EC"]],
  "🚀": ["U+1F680", "rocket", "1f680", ["-", "-"], ["", "U+E5C8"], ["", "U+E10D"], ["󾟭", "U+FE7ED"]],
  "🚤": ["U+1F6A4", "speedboat", "1f6a4", ["", "U+E6A3"], ["", "U+E4B4"], ["", "U+E135"], ["󾟮", "U+FE7EE"]],
  "🚕": ["U+1F695", "taxi", "1f695", ["", "U+E65E"], ["", "U+E4B1"], ["", "U+E15A"], ["󾟯", "U+FE7EF"]],
  "🚚": ["U+1F69A", "delivery truck", "1f69a", ["-", "-"], ["", "U+E4B2"], ["", "U+E42F"], ["󾟱", "U+FE7F1"]],
  "🚒": ["U+1F692", "fire engine", "1f692", ["-", "-"], ["", "U+EADF"], ["", "U+E430"], ["󾟲", "U+FE7F2"]],
  "🚑": ["U+1F691", "ambulance", "1f691", ["-", "-"], ["", "U+EAE0"], ["", "U+E431"], ["󾟳", "U+FE7F3"]],
  "🚓": ["U+1F693", "police car", "1f693", ["-", "-"], ["", "U+EAE1"], ["", "U+E432"], ["󾟴", "U+FE7F4"]],
  "⛽": ["U+26FD", "fuel pump", "26fd", ["", "U+E66B"], ["", "U+E571"], ["", "U+E03A"], ["󾟵", "U+FE7F5"]],
  "🅿": ["U+1F17F", "negative squared latin capital letter p", "1f17f", ["", "U+E66C"], ["", "U+E4A6"], ["", "U+E14F"], ["󾟶", "U+FE7F6"]],
  "🚥": ["U+1F6A5", "horizontal traffic light", "1f6a5", ["", "U+E66D"], ["", "U+E46A"], ["", "U+E14E"], ["󾟷", "U+FE7F7"]],
  "🚧": ["U+1F6A7", "construction sign", "1f6a7", ["-", "-"], ["", "U+E5D7"], ["", "U+E137"], ["󾟸", "U+FE7F8"]],
  "♨": ["U+2668", "hot springs", "2668", ["", "U+E6F7"], ["", "U+E4BC"], ["", "U+E123"], ["󾟺", "U+FE7FA"]],
  "⛺": ["U+26FA", "tent", "26fa", ["-", "-"], ["", "U+E5D0"], ["", "U+E122"], ["󾟻", "U+FE7FB"]],
  "🎡": ["U+1F3A1", "ferris wheel", "1f3a1", ["-", "-"], ["", "U+E46D"], ["", "U+E124"], ["󾟽", "U+FE7FD"]],
  "🎢": ["U+1F3A2", "roller coaster", "1f3a2", ["-", "-"], ["", "U+EAE2"], ["", "U+E433"], ["󾟾", "U+FE7FE"]],
  "🎣": ["U+1F3A3", "fishing pole and fish", "1f3a3", ["", "U+E751"], ["", "U+EB42"], ["", "U+E019"], ["󾟿", "U+FE7FF"]],
  "🎤": ["U+1F3A4", "microphone", "1f3a4", ["", "U+E676"], ["", "U+E503"], ["", "U+E03C"], ["󾠀", "U+FE800"]],
  "🎥": ["U+1F3A5", "movie camera", "1f3a5", ["", "U+E677"], ["", "U+E517"], ["", "U+E03D"], ["󾠁", "U+FE801"]],
  "🎦": ["U+1F3A6", "cinema", "1f3a6", ["", "U+E677"], ["", "U+E517"], ["", "U+E507"], ["󾠂", "U+FE802"]],
  "🎧": ["U+1F3A7", "headphone", "1f3a7", ["", "U+E67A"], ["", "U+E508"], ["", "U+E30A"], ["󾠃", "U+FE803"]],
  "🎨": ["U+1F3A8", "artist palette", "1f3a8", ["", "U+E67B"], ["", "U+E59C"], ["", "U+E502"], ["󾠄", "U+FE804"]],
  "🎩": ["U+1F3A9", "top hat", "1f3a9", ["", "U+E67C"], ["", "U+EAF5"], ["", "U+E503"], ["󾠅", "U+FE805"]],
  "🎫": ["U+1F3AB", "ticket", "1f3ab", ["", "U+E67E"], ["", "U+E49E"], ["", "U+E125"], ["󾠇", "U+FE807"]],
  "🎬": ["U+1F3AC", "clapper board", "1f3ac", ["", "U+E6AC"], ["", "U+E4BE"], ["", "U+E324"], ["󾠈", "U+FE808"]],
  "🀄": ["U+1F004", "mahjong tile red dragon", "1f004", ["-", "-"], ["", "U+E5D1"], ["", "U+E12D"], ["󾠋", "U+FE80B"]],
  "🎯": ["U+1F3AF", "direct hit", "1f3af", ["-", "-"], ["", "U+E4C5"], ["", "U+E130"], ["󾠌", "U+FE80C"]],
  "🎰": ["U+1F3B0", "slot machine", "1f3b0", ["-", "-"], ["", "U+E46E"], ["", "U+E133"], ["󾠍", "U+FE80D"]],
  "🎱": ["U+1F3B1", "billiards", "1f3b1", ["-", "-"], ["", "U+EADD"], ["", "U+E42C"], ["󾠎", "U+FE80E"]],
  "🎵": ["U+1F3B5", "musical note", "1f3b5", ["", "U+E6F6"], ["", "U+E5BE"], ["", "U+E03E"], ["󾠓", "U+FE813"]],
  "🎶": ["U+1F3B6", "multiple musical notes", "1f3b6", ["", "U+E6FF"], ["", "U+E505"], ["", "U+E326"], ["󾠔", "U+FE814"]],
  "🎷": ["U+1F3B7", "saxophone", "1f3b7", ["-", "-"], ["-", "-"], ["", "U+E040"], ["󾠕", "U+FE815"]],
  "🎸": ["U+1F3B8", "guitar", "1f3b8", ["-", "-"], ["", "U+E506"], ["", "U+E041"], ["󾠖", "U+FE816"]],
  "🎺": ["U+1F3BA", "trumpet", "1f3ba", ["-", "-"], ["", "U+EADC"], ["", "U+E042"], ["󾠘", "U+FE818"]], 
  "〽": ["U+303D", "part alternation mark", "303d", ["-", "-"], ["-", "-"], ["", "U+E12C"], ["󾠛", "U+FE81B"]],
  "📷": ["U+1F4F7", "camera", "1f4f7", ["", "U+E681"], ["", "U+E515"], ["", "U+E008"], ["󾓯", "U+FE4EF"]], 
  "📺": ["U+1F4FA", "television", "1f4fa", ["", "U+E68A"], ["", "U+E502"], ["", "U+E12A"], ["󾠜", "U+FE81C"]],
  "📻": ["U+1F4FB", "radio", "1f4fb", ["-", "-"], ["", "U+E5B9"], ["", "U+E128"], ["󾠟", "U+FE81F"]],
  "📼": ["U+1F4FC", "videocassette", "1f4fc", ["-", "-"], ["", "U+E580"], ["", "U+E129"], ["󾠠", "U+FE820"]],
  "💋": ["U+1F48B", "kiss mark", "1f48b", ["", "U+E6F9"], ["", "U+E4EB"], ["", "U+E003"], ["󾠣", "U+FE823"]],
  "💍": ["U+1F48D", "ring", "1f48d", ["", "U+E71B"], ["", "U+E514"], ["", "U+E034"], ["󾠥", "U+FE825"]],
  "💎": ["U+1F48E", "gem stone", "1f48e", ["", "U+E71B"], ["", "U+E514"], ["", "U+E035"], ["󾠦", "U+FE826"]],
  "💏": ["U+1F48F", "kiss", "1f48f", ["", "U+E6F9"], ["", "U+E5CA"], ["", "U+E111"], ["󾠧", "U+FE827"]],
  "💐": ["U+1F490", "bouquet", "1f490", ["-", "-"], ["", "U+EA95"], ["", "U+E306"], ["󾠨", "U+FE828"]],
  "💑": ["U+1F491", "couple with heart", "1f491", ["", "U+E6ED"], ["", "U+EADA"], ["", "U+E425"], ["󾠩", "U+FE829"]],
  "💒": ["U+1F492", "wedding", "1f492", ["-", "-"], ["", "U+E5BB"], ["", "U+E43D"], ["󾠪", "U+FE82A"]],
  "🔞": ["U+1F51E", "no one under eighteen symbol", "1f51e", ["-", "-"], ["", "U+EA83"], ["", "U+E207"], ["󾬥", "U+FEB25"]],
  "📳": ["U+1F4F3", "vibration mode", "1f4f3", ["-", "-"], ["", "U+EA90"], ["", "U+E250"], ["󾠹", "U+FE839"]],
  "📴": ["U+1F4F4", "mobile phone off", "1f4f4", ["-", "-"], ["", "U+EA91"], ["", "U+E251"], ["󾠺", "U+FE83A"]],
  "🍔": ["U+1F354", "hamburger", "1f354", ["", "U+E673"], ["", "U+E4D6"], ["", "U+E120"], ["󾥠", "U+FE960"]],
  "🍙": ["U+1F359", "rice ball", "1f359", ["", "U+E749"], ["", "U+E4D5"], ["", "U+E342"], ["󾥡", "U+FE961"]],
  "🍰": ["U+1F370", "shortcake", "1f370", ["", "U+E74A"], ["", "U+E4D0"], ["", "U+E046"], ["󾥢", "U+FE962"]],
  "🍜": ["U+1F35C", "steaming bowl", "1f35c", ["", "U+E74C"], ["", "U+E5B4"], ["", "U+E340"], ["󾥣", "U+FE963"]],
  "🍞": ["U+1F35E", "bread", "1f35e", ["", "U+E74D"], ["", "U+EAAF"], ["", "U+E339"], ["󾥤", "U+FE964"]],
  "🍳": ["U+1F373", "cooking", "1f373", ["-", "-"], ["", "U+E4D1"], ["", "U+E147"], ["󾥥", "U+FE965"]],
  "🍦": ["U+1F366", "soft ice cream", "1f366", ["-", "-"], ["", "U+EAB0"], ["", "U+E33A"], ["󾥦", "U+FE966"]],
  "🍟": ["U+1F35F", "french fries", "1f35f", ["-", "-"], ["", "U+EAB1"], ["", "U+E33B"], ["󾥧", "U+FE967"]],
  "🍡": ["U+1F361", "dango", "1f361", ["-", "-"], ["", "U+EAB2"], ["", "U+E33C"], ["󾥨", "U+FE968"]],
  "🍘": ["U+1F358", "rice cracker", "1f358", ["-", "-"], ["", "U+EAB3"], ["", "U+E33D"], ["󾥩", "U+FE969"]],
  "🍚": ["U+1F35A", "cooked rice", "1f35a", ["", "U+E74C"], ["", "U+EAB4"], ["", "U+E33E"], ["󾥪", "U+FE96A"]],
  "🍝": ["U+1F35D", "spaghetti", "1f35d", ["-", "-"], ["", "U+EAB5"], ["", "U+E33F"], ["󾥫", "U+FE96B"]],
  "🍛": ["U+1F35B", "curry and rice", "1f35b", ["-", "-"], ["", "U+EAB6"], ["", "U+E341"], ["󾥬", "U+FE96C"]],
  "🍢": ["U+1F362", "oden", "1f362", ["-", "-"], ["", "U+EAB7"], ["", "U+E343"], ["󾥭", "U+FE96D"]],
  "🍣": ["U+1F363", "sushi", "1f363", ["-", "-"], ["", "U+EAB8"], ["", "U+E344"], ["󾥮", "U+FE96E"]],
  "🍱": ["U+1F371", "bento box", "1f371", ["-", "-"], ["", "U+EABD"], ["", "U+E34C"], ["󾥯", "U+FE96F"]],
  "🍲": ["U+1F372", "pot of food", "1f372", ["-", "-"], ["", "U+EABE"], ["", "U+E34D"], ["󾥰", "U+FE970"]],
  "🍧": ["U+1F367", "shaved ice", "1f367", ["-", "-"], ["", "U+EAEA"], ["", "U+E43F"], ["󾥱", "U+FE971"]],
  "🍴": ["U+1F374", "fork and knife", "1f374", ["", "U+E66F"], ["", "U+E4AC"], ["", "U+E043"], ["󾦀", "U+FE980"]],
  "☕": ["U+2615", "hot beverage", "2615", ["", "U+E670"], ["", "U+E597"], ["", "U+E045"], ["󾦁", "U+FE981"]],
  "🍸": ["U+1F378", "cocktail glass", "1f378", ["", "U+E671"], ["", "U+E4C2"], ["", "U+E044"], ["󾦂", "U+FE982"]],
  "🍺": ["U+1F37A", "beer mug", "1f37a", ["", "U+E672"], ["", "U+E4C3"], ["", "U+E047"], ["󾦃", "U+FE983"]],
  "🍵": ["U+1F375", "teacup without handle", "1f375", ["", "U+E71E"], ["", "U+EAAE"], ["", "U+E338"], ["󾦄", "U+FE984"]],
  "🍶": ["U+1F376", "sake bottle and cup", "1f376", ["", "U+E74B"], ["", "U+EA97"], ["", "U+E30B"], ["󾦅", "U+FE985"]], 
  "🍻": ["U+1F37B", "clinking beer mugs", "1f37b", ["", "U+E672"], ["", "U+EA98"], ["", "U+E30C"], ["󾦇", "U+FE987"]],
  "↗": ["U+2197", "north east arrow", "2197", ["", "U+E678"], ["", "U+E555"], ["", "U+E236"], ["󾫰", "U+FEAF0"]],
  "↘": ["U+2198", "south east arrow", "2198", ["", "U+E696"], ["", "U+E54D"], ["", "U+E238"], ["󾫱", "U+FEAF1"]],
  "↖": ["U+2196", "north west arrow", "2196", ["", "U+E697"], ["", "U+E54C"], ["", "U+E237"], ["󾫲", "U+FEAF2"]],
  "↙": ["U+2199", "south west arrow", "2199", ["", "U+E6A5"], ["", "U+E556"], ["", "U+E239"], ["󾫳", "U+FEAF3"]],
  "⬆": ["U+2B06", "upwards black arrow", "2b06", ["-", "-"], ["", "U+E53F"], ["", "U+E232"], ["󾫸", "U+FEAF8"]],
  "⬇": ["U+2B07", "downwards black arrow", "2b07", ["-", "-"], ["", "U+E540"], ["", "U+E233"], ["󾫹", "U+FEAF9"]],
  "➡": ["U+27A1", "black rightwards arrow", "27a1", ["-", "-"], ["", "U+E552"], ["", "U+E234"], ["󾫺", "U+FEAFA"]],
  "⬅": ["U+2B05", "leftwards black arrow", "2b05", ["-", "-"], ["", "U+E553"], ["", "U+E235"], ["󾫻", "U+FEAFB"]],
  "▶": ["U+25B6", "black right-pointing triangle", "25b6", ["-", "-"], ["", "U+E52E"], ["", "U+E23A"], ["󾫼", "U+FEAFC"]],
  "◀": ["U+25C0", "black left-pointing triangle", "25c0", ["-", "-"], ["", "U+E52D"], ["", "U+E23B"], ["󾫽", "U+FEAFD"]],
  "⏩": ["U+23E9", "black right-pointing double triangle", "23e9", ["-", "-"], ["", "U+E530"], ["", "U+E23C"], ["󾫾", "U+FEAFE"]],
  "⏪": ["U+23EA", "black left-pointing double triangle", "23ea", ["-", "-"], ["", "U+E52F"], ["", "U+E23D"], ["󾫿", "U+FEAFF"]],
  "⭕": ["U+2B55", "heavy large circle", "2b55", ["", "U+E6A0"], ["", "U+EAAD"], ["", "U+E332"], ["󾭄", "U+FEB44"]],
  "❌": ["U+274C", "cross mark", "274c", ["-", "-"], ["", "U+E550"], ["", "U+E333"], ["󾭅", "U+FEB45"]],
  "❗": ["U+2757", "heavy exclamation mark symbol", "2757", ["", "U+E702"], ["", "U+E482"], ["", "U+E021"], ["󾬄", "U+FEB04"]],
  "❓": ["U+2753", "black question mark ornament", "2753", ["-", "-"], ["", "U+E483"], ["", "U+E020"], ["󾬉", "U+FEB09"]],
  "❔": ["U+2754", "white question mark ornament", "2754", ["-", "-"], ["", "U+E483"], ["", "U+E336"], ["󾬊", "U+FEB0A"]],
  "❕": ["U+2755", "white exclamation mark ornament", "2755", ["", "U+E702"], ["", "U+E482"], ["", "U+E337"], ["󾬋", "U+FEB0B"]],
  "➿": ["U+27BF", "double curly loop", "27bf", ["", "U+E6DF"], ["-", "-"], ["", "U+E211"], ["󾠫", "U+FE82B"]],
  "❤": ["U+2764", "heavy black heart", "2764", ["", "U+E6EC"], ["", "U+E595"], ["", "U+E022"], ["󾬌", "U+FEB0C"]],
  "💓": ["U+1F493", "beating heart", "1f493", ["", "U+E6ED"], ["", "U+EB75"], ["", "U+E327"], ["󾬍", "U+FEB0D"]],
  "💔": ["U+1F494", "broken heart", "1f494", ["", "U+E6EE"], ["", "U+E477"], ["", "U+E023"], ["󾬎", "U+FEB0E"]],
  "💗": ["U+1F497", "growing heart", "1f497", ["", "U+E6ED"], ["", "U+EB75"], ["", "U+E328"], ["󾬑", "U+FEB11"]],
  "💘": ["U+1F498", "heart with arrow", "1f498", ["", "U+E6EC"], ["", "U+E4EA"], ["", "U+E329"], ["󾬒", "U+FEB12"]],
  "💙": ["U+1F499", "blue heart", "1f499", ["", "U+E6EC"], ["", "U+EAA7"], ["", "U+E32A"], ["󾬓", "U+FEB13"]],
  "💚": ["U+1F49A", "green heart", "1f49a", ["", "U+E6EC"], ["", "U+EAA8"], ["", "U+E32B"], ["󾬔", "U+FEB14"]],
  "💛": ["U+1F49B", "yellow heart", "1f49b", ["", "U+E6EC"], ["", "U+EAA9"], ["", "U+E32C"], ["󾬕", "U+FEB15"]],
  "💜": ["U+1F49C", "purple heart", "1f49c", ["", "U+E6EC"], ["", "U+EAAA"], ["", "U+E32D"], ["󾬖", "U+FEB16"]],
  "💝": ["U+1F49D", "heart with ribbon", "1f49d", ["", "U+E6EC"], ["", "U+EB54"], ["", "U+E437"], ["󾬗", "U+FEB17"]],
  "💟": ["U+1F49F", "heart decoration", "1f49f", ["", "U+E6F8"], ["", "U+E595"], ["", "U+E204"], ["󾬙", "U+FEB19"]],
  "♥": ["U+2665", "black heart suit", "2665", ["", "U+E68D"], ["", "U+EAA5"], ["", "U+E20C"], ["󾬚", "U+FEB1A"]],
  "♠": ["U+2660", "black spade suit", "2660", ["", "U+E68E"], ["", "U+E5A1"], ["", "U+E20E"], ["󾬛", "U+FEB1B"]],
  "♦": ["U+2666", "black diamond suit", "2666", ["", "U+E68F"], ["", "U+E5A2"], ["", "U+E20D"], ["󾬜", "U+FEB1C"]],
  "♣": ["U+2663", "black club suit", "2663", ["", "U+E690"], ["", "U+E5A3"], ["", "U+E20F"], ["󾬝", "U+FEB1D"]],
  "🚬": ["U+1F6AC", "smoking symbol", "1f6ac", ["", "U+E67F"], ["", "U+E47D"], ["", "U+E30E"], ["󾬞", "U+FEB1E"]],
  "🚭": ["U+1F6AD", "no smoking symbol", "1f6ad", ["", "U+E680"], ["", "U+E47E"], ["", "U+E208"], ["󾬟", "U+FEB1F"]],
  "♿": ["U+267F", "wheelchair symbol", "267f", ["", "U+E69B"], ["", "U+E47F"], ["", "U+E20A"], ["󾬠", "U+FEB20"]],
  "⚠": ["U+26A0", "warning sign", "26a0", ["", "U+E737"], ["", "U+E481"], ["", "U+E252"], ["󾬣", "U+FEB23"]],
  "🚲": ["U+1F6B2", "bicycle", "1f6b2", ["", "U+E71D"], ["", "U+E4AE"], ["", "U+E136"], ["󾟫", "U+FE7EB"]],
  "🚶": ["U+1F6B6", "pedestrian", "1f6b6", ["", "U+E733"], ["", "U+EB72"], ["", "U+E201"], ["󾟰", "U+FE7F0"]],
  "🚹": ["U+1F6B9", "mens symbol", "1f6b9", ["-", "-"], ["-", "-"], ["", "U+E138"], ["󾬳", "U+FEB33"]],
  "🚺": ["U+1F6BA", "womens symbol", "1f6ba", ["-", "-"], ["-", "-"], ["", "U+E139"], ["󾬴", "U+FEB34"]],
  "🛀": ["U+1F6C0", "bath", "1f6c0", ["", "U+E6F7"], ["", "U+E5D8"], ["", "U+E13F"], ["󾔅", "U+FE505"]],
  "🚻": ["U+1F6BB", "restroom", "1f6bb", ["", "U+E66E"], ["", "U+E4A5"], ["", "U+E151"], ["󾔆", "U+FE506"]],
  "🚽": ["U+1F6BD", "toilet", "1f6bd", ["", "U+E66E"], ["", "U+E4A5"], ["", "U+E140"], ["󾔇", "U+FE507"]],
  "🚾": ["U+1F6BE", "water closet", "1f6be", ["", "U+E66E"], ["", "U+E4A5"], ["", "U+E309"], ["󾔈", "U+FE508"]],
  "🚼": ["U+1F6BC", "baby symbol", "1f6bc", ["-", "-"], ["", "U+EB18"], ["", "U+E13A"], ["󾬵", "U+FEB35"]],
  "🆒": ["U+1F192", "squared cool", "1f192", ["-", "-"], ["", "U+EA85"], ["", "U+E214"], ["󾬸", "U+FEB38"]],
  "🆔": ["U+1F194", "squared id", "1f194", ["", "U+E6D8"], ["", "U+EA88"], ["", "U+E229"], ["󾮁", "U+FEB81"]],
  "🆕": ["U+1F195", "squared new", "1f195", ["", "U+E6DD"], ["", "U+E5B5"], ["", "U+E212"], ["󾬶", "U+FEB36"]],
  "🆗": ["U+1F197", "squared ok", "1f197", ["", "U+E70B"], ["", "U+E5AD"], ["", "U+E24D"], ["󾬧", "U+FEB27"]],
  "🆙": ["U+1F199", "squared up with exclamation mark", "1f199", ["-", "-"], ["", "U+E50F"], ["", "U+E213"], ["󾬷", "U+FEB37"]],
  "🆚": ["U+1F19A", "squared vs", "1f19a", ["-", "-"], ["", "U+E5D2"], ["", "U+E12E"], ["󾬲", "U+FEB32"]],
  "🈁": ["U+1F201", "squared katakana koko", "1f201", ["-", "-"], ["-", "-"], ["", "U+E203"], ["󾬤", "U+FEB24"]],
  "🈂": ["U+1F202", "squared katakana sa", "1f202", ["-", "-"], ["", "U+EA87"], ["", "U+E228"], ["󾬿", "U+FEB3F"]],
  "🈳": ["U+1F233", "squared cjk unified ideograph-7a7a", "1f233", ["", "U+E739"], ["", "U+EA8A"], ["", "U+E22B"], ["󾬯", "U+FEB2F"]],
  "🈵": ["U+1F235", "squared cjk unified ideograph-6e80", "1f235", ["", "U+E73B"], ["", "U+EA89"], ["", "U+E22A"], ["󾬱", "U+FEB31"]],
  "🈶": ["U+1F236", "squared cjk unified ideograph-6709", "1f236", ["-", "-"], ["-", "-"], ["", "U+E215"], ["󾬹", "U+FEB39"]],
  "🈚": ["U+1F21A", "squared cjk unified ideograph-7121", "1f21a", ["-", "-"], ["-", "-"], ["", "U+E216"], ["󾬺", "U+FEB3A"]],
  "🈷": ["U+1F237", "squared cjk unified ideograph-6708", "1f237", ["-", "-"], ["-", "-"], ["", "U+E217"], ["󾬻", "U+FEB3B"]],
  "🈸": ["U+1F238", "squared cjk unified ideograph-7533", "1f238", ["-", "-"], ["-", "-"], ["", "U+E218"], ["󾬼", "U+FEB3C"]],
  "🈹": ["U+1F239", "squared cjk unified ideograph-5272", "1f239", ["-", "-"], ["", "U+EA86"], ["", "U+E227"], ["󾬾", "U+FEB3E"]],
  "🈯": ["U+1F22F", "squared cjk unified ideograph-6307", "1f22f", ["-", "-"], ["", "U+EA8B"], ["", "U+E22C"], ["󾭀", "U+FEB40"]],
  "🈺": ["U+1F23A", "squared cjk unified ideograph-55b6", "1f23a", ["-", "-"], ["", "U+EA8C"], ["", "U+E22D"], ["󾭁", "U+FEB41"]],
  "㊙": ["U+3299", "circled ideograph secret", "3299", ["", "U+E734"], ["", "U+E4F1"], ["", "U+E315"], ["󾬫", "U+FEB2B"]],
  "㊗": ["U+3297", "circled ideograph congratulation", "3297", ["-", "-"], ["", "U+EA99"], ["", "U+E30D"], ["󾭃", "U+FEB43"]],
  "🉐": ["U+1F250", "circled ideograph advantage", "1f250", ["-", "-"], ["", "U+E4F7"], ["", "U+E226"], ["󾬽", "U+FEB3D"]],
  "💡": ["U+1F4A1", "electric light bulb", "1f4a1", ["", "U+E6FB"], ["", "U+E476"], ["", "U+E10F"], ["󾭖", "U+FEB56"]],
  "💢": ["U+1F4A2", "anger symbol", "1f4a2", ["", "U+E6FC"], ["", "U+E4E5"], ["", "U+E334"], ["󾭗", "U+FEB57"]],
  "💣": ["U+1F4A3", "bomb", "1f4a3", ["", "U+E6FE"], ["", "U+E47A"], ["", "U+E311"], ["󾭘", "U+FEB58"]],
  "💤": ["U+1F4A4", "sleeping symbol", "1f4a4", ["", "U+E701"], ["", "U+E475"], ["", "U+E13C"], ["󾭙", "U+FEB59"]],
  "💦": ["U+1F4A6", "splashing sweat symbol", "1f4a6", ["", "U+E706"], ["", "U+E5B1"], ["", "U+E331"], ["󾭛", "U+FEB5B"]],
  "💧": ["U+1F4A7", "droplet", "1f4a7", ["", "U+E707"], ["", "U+E4E6"], ["", "U+E331"], ["󾭜", "U+FEB5C"]],
  "💨": ["U+1F4A8", "dash symbol", "1f4a8", ["", "U+E708"], ["", "U+E4F4"], ["", "U+E330"], ["󾭝", "U+FEB5D"]],
  "💩": ["U+1F4A9", "pile of poo", "1f4a9", ["-", "-"], ["", "U+E4F5"], ["", "U+E05A"], ["󾓴", "U+FE4F4"]],
  "💪": ["U+1F4AA", "flexed biceps", "1f4aa", ["-", "-"], ["", "U+E4E9"], ["", "U+E14C"], ["󾭞", "U+FEB5E"]],
  "✨": ["U+2728", "sparkles", "2728", ["", "U+E6FA"], ["", "U+EAAB"], ["", "U+E32E"], ["󾭠", "U+FEB60"]],
  "🌟": ["U+1F31F", "glowing star", "1f31f", ["-", "-"], ["", "U+E48B"], ["", "U+E335"], ["󾭩", "U+FEB69"]],
  "✴": ["U+2734", "eight pointed black star", "2734", ["", "U+E6F8"], ["", "U+E479"], ["", "U+E205"], ["󾭡", "U+FEB61"]],
  "✳": ["U+2733", "eight spoked asterisk", "2733", ["", "U+E6F8"], ["", "U+E53E"], ["", "U+E206"], ["󾭢", "U+FEB62"]],
  "🔴": ["U+1F534", "large red circle", "1f534", ["", "U+E69C"], ["", "U+E54A"], ["", "U+E219"], ["󾭣", "U+FEB63"]],
  "🔲": ["U+1F532", "black square button", "1f532", ["", "U+E69C"], ["", "U+E54B"], ["", "U+E21A"], ["󾭤", "U+FEB64"]],
  "🔳": ["U+1F533", "white square button", "1f533", ["", "U+E69C"], ["", "U+E54B"], ["", "U+E21B"], ["󾭧", "U+FEB67"]],
  "🔊": ["U+1F50A", "speaker with three sound waves", "1f50a", ["-", "-"], ["", "U+E511"], ["", "U+E141"], ["󾠡", "U+FE821"]],
  "🔍": ["U+1F50D", "left-pointing magnifying glass", "1f50d", ["", "U+E6DC"], ["", "U+E518"], ["", "U+E114"], ["󾮅", "U+FEB85"]],
  "🔒": ["U+1F512", "lock", "1f512", ["", "U+E6D9"], ["", "U+E51C"], ["", "U+E144"], ["󾮆", "U+FEB86"]],
  "🔓": ["U+1F513", "open lock", "1f513", ["", "U+E6D9"], ["", "U+E51C"], ["", "U+E145"], ["󾮇", "U+FEB87"]],
  "🔑": ["U+1F511", "key", "1f511", ["", "U+E6D9"], ["", "U+E519"], ["", "U+E03F"], ["󾮂", "U+FEB82"]],
  "🔔": ["U+1F514", "bell", "1f514", ["", "U+E713"], ["", "U+E512"], ["", "U+E325"], ["󾓲", "U+FE4F2"]],
  "🔝": ["U+1F51D", "top with upwards arrow above", "1f51d", ["-", "-"], ["-", "-"], ["", "U+E24C"], ["󾭂", "U+FEB42"]],
  "✊": ["U+270A", "raised fist", "270a", ["", "U+E693"], ["", "U+EB83"], ["", "U+E010"], ["󾮓", "U+FEB93"]],
  "✋": ["U+270B", "raised hand", "270b", ["", "U+E695"], ["", "U+E5A7"], ["", "U+E012"], ["󾮕", "U+FEB95"]],
  "✌": ["U+270C", "victory hand", "270c", ["", "U+E694"], ["", "U+E5A6"], ["", "U+E011"], ["󾮔", "U+FEB94"]],
  "👊": ["U+1F44A", "fisted hand sign", "1f44a", ["", "U+E6FD"], ["", "U+E4F3"], ["", "U+E00D"], ["󾮖", "U+FEB96"]],
  "👍": ["U+1F44D", "thumbs up sign", "1f44d", ["", "U+E727"], ["", "U+E4F9"], ["", "U+E00E"], ["󾮗", "U+FEB97"]],
  "☝": ["U+261D", "white up pointing index", "261d", ["-", "-"], ["", "U+E4F6"], ["", "U+E00F"], ["󾮘", "U+FEB98"]],
  "👆": ["U+1F446", "white up pointing backhand index", "1f446", ["-", "-"], ["", "U+EA8D"], ["", "U+E22E"], ["󾮙", "U+FEB99"]],
  "👇": ["U+1F447", "white down pointing backhand index", "1f447", ["-", "-"], ["", "U+EA8E"], ["", "U+E22F"], ["󾮚", "U+FEB9A"]],
  "👈": ["U+1F448", "white left pointing backhand index", "1f448", ["-", "-"], ["", "U+E4FF"], ["", "U+E230"], ["󾮛", "U+FEB9B"]],
  "👉": ["U+1F449", "white right pointing backhand index", "1f449", ["-", "-"], ["", "U+E500"], ["", "U+E231"], ["󾮜", "U+FEB9C"]],
  "👋": ["U+1F44B", "waving hand sign", "1f44b", ["", "U+E695"], ["", "U+EAD6"], ["", "U+E41E"], ["󾮝", "U+FEB9D"]],
  "👏": ["U+1F44F", "clapping hands sign", "1f44f", ["-", "-"], ["", "U+EAD3"], ["", "U+E41F"], ["󾮞", "U+FEB9E"]],
  "👌": ["U+1F44C", "ok hand sign", "1f44c", ["", "U+E70B"], ["", "U+EAD4"], ["", "U+E420"], ["󾮟", "U+FEB9F"]],
  "👎": ["U+1F44E", "thumbs down sign", "1f44e", ["", "U+E700"], ["", "U+EAD5"], ["", "U+E421"], ["󾮠", "U+FEBA0"]],
  "👐": ["U+1F450", "open hands sign", "1f450", ["", "U+E695"], ["", "U+EAD6"], ["", "U+E422"], ["󾮡", "U+FEBA1"]],
  "☀": ["U+2600", "black sun with rays", "2600", ["", "U+E63E"], ["", "U+E488"], ["", "U+E04A"], ["󾀀", "U+FE000"]],
  "☁": ["U+2601", "cloud", "2601", ["", "U+E63F"], ["", "U+E48D"], ["", "U+E049"], ["󾀁", "U+FE001"]],
  "☔": ["U+2614", "umbrella with rain drops", "2614", ["", "U+E640"], ["", "U+E48C"], ["", "U+E04B"], ["󾀂", "U+FE002"]],
  "⛄": ["U+26C4", "snowman without snow", "26c4", ["", "U+E641"], ["", "U+E485"], ["", "U+E048"], ["󾀃", "U+FE003"]],
  "⚡": ["U+26A1", "high voltage sign", "26a1", ["", "U+E642"], ["", "U+E487"], ["", "U+E13D"], ["󾀄", "U+FE004"]],
  "🌀": ["U+1F300", "cyclone", "1f300", ["", "U+E643"], ["", "U+E469"], ["", "U+E443"], ["󾀅", "U+FE005"]],
  "🌂": ["U+1F302", "closed umbrella", "1f302", ["", "U+E645"], ["", "U+EAE8"], ["", "U+E43C"], ["󾀇", "U+FE007"]],
  "🌃": ["U+1F303", "night with stars", "1f303", ["", "U+E6B3"], ["", "U+EAF1"], ["", "U+E44B"], ["󾀈", "U+FE008"]],
  "🌄": ["U+1F304", "sunrise over mountains", "1f304", ["", "U+E63E"], ["", "U+EAF4"], ["", "U+E04D"], ["󾀉", "U+FE009"]],
  "🌅": ["U+1F305", "sunrise", "1f305", ["", "U+E63E"], ["", "U+EAF4"], ["", "U+E449"], ["󾀊", "U+FE00A"]],
  "🌆": ["U+1F306", "cityscape at dusk", "1f306", ["-", "-"], ["", "U+E5DA"], ["", "U+E146"], ["󾀋", "U+FE00B"]],
  "🌇": ["U+1F307", "sunset over buildings", "1f307", ["", "U+E63E"], ["", "U+E5DA"], ["", "U+E44A"], ["󾀌", "U+FE00C"]],
  "🌈": ["U+1F308", "rainbow", "1f308", ["-", "-"], ["", "U+EAF2"], ["", "U+E44C"], ["󾀍", "U+FE00D"]],
  "🌊": ["U+1F30A", "water wave", "1f30a", ["", "U+E73F"], ["", "U+EB7C"], ["", "U+E43E"], ["󾀸", "U+FE038"]],
  "🌙": ["U+1F319", "crescent moon", "1f319", ["", "U+E69F"], ["", "U+E486"], ["", "U+E04C"], ["󾀔", "U+FE014"]],
  "🕐": ["U+1F550", "clock face one oclock", "1f550", ["", "U+E6BA"], ["", "U+E594"], ["", "U+E024"], ["󾀞", "U+FE01E"]],
  "🕑": ["U+1F551", "clock face two oclock", "1f551", ["", "U+E6BA"], ["", "U+E594"], ["", "U+E025"], ["󾀟", "U+FE01F"]],
  "🕒": ["U+1F552", "clock face three oclock", "1f552", ["", "U+E6BA"], ["", "U+E594"], ["", "U+E026"], ["󾀠", "U+FE020"]],
  "🕓": ["U+1F553", "clock face four oclock", "1f553", ["", "U+E6BA"], ["", "U+E594"], ["", "U+E027"], ["󾀡", "U+FE021"]],
  "🕔": ["U+1F554", "clock face five oclock", "1f554", ["", "U+E6BA"], ["", "U+E594"], ["", "U+E028"], ["󾀢", "U+FE022"]],
  "🕕": ["U+1F555", "clock face six oclock", "1f555", ["", "U+E6BA"], ["", "U+E594"], ["", "U+E029"], ["󾀣", "U+FE023"]],
  "🕖": ["U+1F556", "clock face seven oclock", "1f556", ["", "U+E6BA"], ["", "U+E594"], ["", "U+E02A"], ["󾀤", "U+FE024"]],
  "🕗": ["U+1F557", "clock face eight oclock", "1f557", ["", "U+E6BA"], ["", "U+E594"], ["", "U+E02B"], ["󾀥", "U+FE025"]],
  "🕘": ["U+1F558", "clock face nine oclock", "1f558", ["", "U+E6BA"], ["", "U+E594"], ["", "U+E02C"], ["󾀦", "U+FE026"]],
  "🕙": ["U+1F559", "clock face ten oclock", "1f559", ["", "U+E6BA"], ["", "U+E594"], ["", "U+E02D"], ["󾀧", "U+FE027"]],
  "🕚": ["U+1F55A", "clock face eleven oclock", "1f55a", ["", "U+E6BA"], ["", "U+E594"], ["", "U+E02E"], ["󾀨", "U+FE028"]],
  "🕛": ["U+1F55B", "clock face twelve oclock", "1f55b", ["", "U+E6BA"], ["", "U+E594"], ["", "U+E02F"], ["󾀩", "U+FE029"]],
  "♈": ["U+2648", "aries", "2648", ["", "U+E646"], ["", "U+E48F"], ["", "U+E23F"], ["󾀫", "U+FE02B"]],
  "♉": ["U+2649", "taurus", "2649", ["", "U+E647"], ["", "U+E490"], ["", "U+E240"], ["󾀬", "U+FE02C"]],
  "♊": ["U+264A", "gemini", "264a", ["", "U+E648"], ["", "U+E491"], ["", "U+E241"], ["󾀭", "U+FE02D"]],
  "♋": ["U+264B", "cancer", "264b", ["", "U+E649"], ["", "U+E492"], ["", "U+E242"], ["󾀮", "U+FE02E"]],
  "♌": ["U+264C", "leo", "264c", ["", "U+E64A"], ["", "U+E493"], ["", "U+E243"], ["󾀯", "U+FE02F"]],
  "♍": ["U+264D", "virgo", "264d", ["", "U+E64B"], ["", "U+E494"], ["", "U+E244"], ["󾀰", "U+FE030"]],
  "♎": ["U+264E", "libra", "264e", ["", "U+E64C"], ["", "U+E495"], ["", "U+E245"], ["󾀱", "U+FE031"]],
  "♏": ["U+264F", "scorpius", "264f", ["", "U+E64D"], ["", "U+E496"], ["", "U+E246"], ["󾀲", "U+FE032"]],
  "♐": ["U+2650", "sagittarius", "2650", ["", "U+E64E"], ["", "U+E497"], ["", "U+E247"], ["󾀳", "U+FE033"]],
  "♑": ["U+2651", "capricorn", "2651", ["", "U+E64F"], ["", "U+E498"], ["", "U+E248"], ["󾀴", "U+FE034"]],
  "♒": ["U+2652", "aquarius", "2652", ["", "U+E650"], ["", "U+E499"], ["", "U+E249"], ["󾀵", "U+FE035"]],
  "♓": ["U+2653", "pisces", "2653", ["", "U+E651"], ["", "U+E49A"], ["", "U+E24A"], ["󾀶", "U+FE036"]],
  "⛎": ["U+26CE", "ophiuchus", "26ce", ["-", "-"], ["", "U+E49B"], ["", "U+E24B"], ["󾀷", "U+FE037"]],
  "🍀": ["U+1F340", "four leaf clover", "1f340", ["", "U+E741"], ["", "U+E513"], ["", "U+E110"], ["󾀼", "U+FE03C"]],
  "🌷": ["U+1F337", "tulip", "1f337", ["", "U+E743"], ["", "U+E4E4"], ["", "U+E304"], ["󾀽", "U+FE03D"]],
  "🍁": ["U+1F341", "maple leaf", "1f341", ["", "U+E747"], ["", "U+E4CE"], ["", "U+E118"], ["󾀿", "U+FE03F"]],
  "🌸": ["U+1F338", "cherry blossom", "1f338", ["", "U+E748"], ["", "U+E4CA"], ["", "U+E030"], ["󾁀", "U+FE040"]],
  "🌹": ["U+1F339", "rose", "1f339", ["-", "-"], ["", "U+E5BA"], ["", "U+E032"], ["󾁁", "U+FE041"]],
  "🍂": ["U+1F342", "fallen leaf", "1f342", ["", "U+E747"], ["", "U+E5CD"], ["", "U+E119"], ["󾁂", "U+FE042"]],
  "🍃": ["U+1F343", "leaf fluttering in wind", "1f343", ["-", "-"], ["", "U+E5CD"], ["", "U+E447"], ["󾁃", "U+FE043"]],
  "🌺": ["U+1F33A", "hibiscus", "1f33a", ["-", "-"], ["", "U+EA94"], ["", "U+E303"], ["󾁅", "U+FE045"]],
  "🌻": ["U+1F33B", "sunflower", "1f33b", ["-", "-"], ["", "U+E4E3"], ["", "U+E305"], ["󾁆", "U+FE046"]],
  "🌴": ["U+1F334", "palm tree", "1f334", ["-", "-"], ["", "U+E4E2"], ["", "U+E307"], ["󾁇", "U+FE047"]],
  "🌵": ["U+1F335", "cactus", "1f335", ["-", "-"], ["", "U+EA96"], ["", "U+E308"], ["󾁈", "U+FE048"]],
  "🌾": ["U+1F33E", "ear of rice", "1f33e", ["-", "-"], ["-", "-"], ["", "U+E444"], ["󾁉", "U+FE049"]],
  "🍎": ["U+1F34E", "red apple", "1f34e", ["", "U+E745"], ["", "U+EAB9"], ["", "U+E345"], ["󾁑", "U+FE051"]],
  "🍊": ["U+1F34A", "tangerine", "1f34a", ["-", "-"], ["", "U+EABA"], ["", "U+E346"], ["󾁒", "U+FE052"]],
  "🍓": ["U+1F353", "strawberry", "1f353", ["-", "-"], ["", "U+E4D4"], ["", "U+E347"], ["󾁓", "U+FE053"]],
  "🍉": ["U+1F349", "watermelon", "1f349", ["-", "-"], ["", "U+E4CD"], ["", "U+E348"], ["󾁔", "U+FE054"]],
  "🍅": ["U+1F345", "tomato", "1f345", ["-", "-"], ["", "U+EABB"], ["", "U+E349"], ["󾁕", "U+FE055"]],
  "🍆": ["U+1F346", "aubergine", "1f346", ["-", "-"], ["", "U+EABC"], ["", "U+E34A"], ["󾁖", "U+FE056"]],
  "👀": ["U+1F440", "eyes", "1f440", ["", "U+E691"], ["", "U+E5A4"], ["", "U+E419"], ["󾆐", "U+FE190"]],
  "👂": ["U+1F442", "ear", "1f442", ["", "U+E692"], ["", "U+E5A5"], ["", "U+E41B"], ["󾆑", "U+FE191"]],
  "👃": ["U+1F443", "nose", "1f443", ["-", "-"], ["", "U+EAD0"], ["", "U+E41A"], ["󾆒", "U+FE192"]],
  "👄": ["U+1F444", "mouth", "1f444", ["", "U+E6F9"], ["", "U+EAD1"], ["", "U+E41C"], ["󾆓", "U+FE193"]],
  "👦": ["U+1F466", "boy", "1f466", ["", "U+E6F0"], ["", "U+E4FC"], ["", "U+E001"], ["󾆛", "U+FE19B"]],
  "👧": ["U+1F467", "girl", "1f467", ["", "U+E6F0"], ["", "U+E4FA"], ["", "U+E002"], ["󾆜", "U+FE19C"]],
  "👨": ["U+1F468", "man", "1f468", ["", "U+E6F0"], ["", "U+E4FC"], ["", "U+E004"], ["󾆝", "U+FE19D"]],
  "👩": ["U+1F469", "woman", "1f469", ["", "U+E6F0"], ["", "U+E4FA"], ["", "U+E005"], ["󾆞", "U+FE19E"]],
  "👫": ["U+1F46B", "man and woman holding hands", "1f46b", ["-", "-"], ["-", "-"], ["", "U+E428"], ["󾆠", "U+FE1A0"]],
  "👮": ["U+1F46E", "police officer", "1f46e", ["-", "-"], ["", "U+E5DD"], ["", "U+E152"], ["󾆡", "U+FE1A1"]],
  "👯": ["U+1F46F", "woman with bunny ears", "1f46f", ["-", "-"], ["", "U+EADB"], ["", "U+E429"], ["󾆢", "U+FE1A2"]],
  "👱": ["U+1F471", "person with blond hair", "1f471", ["-", "-"], ["", "U+EB13"], ["", "U+E515"], ["󾆤", "U+FE1A4"]],
  "👲": ["U+1F472", "man with gua pi mao", "1f472", ["-", "-"], ["", "U+EB14"], ["", "U+E516"], ["󾆥", "U+FE1A5"]],
  "👳": ["U+1F473", "man with turban", "1f473", ["-", "-"], ["", "U+EB15"], ["", "U+E517"], ["󾆦", "U+FE1A6"]],
  "👴": ["U+1F474", "older man", "1f474", ["-", "-"], ["", "U+EB16"], ["", "U+E518"], ["󾆧", "U+FE1A7"]],
  "👵": ["U+1F475", "older woman", "1f475", ["-", "-"], ["", "U+EB17"], ["", "U+E519"], ["󾆨", "U+FE1A8"]],
  "👶": ["U+1F476", "baby", "1f476", ["-", "-"], ["", "U+EB18"], ["", "U+E51A"], ["󾆩", "U+FE1A9"]],
  "👷": ["U+1F477", "construction worker", "1f477", ["-", "-"], ["", "U+EB19"], ["", "U+E51B"], ["󾆪", "U+FE1AA"]],
  "👸": ["U+1F478", "princess", "1f478", ["-", "-"], ["", "U+EB1A"], ["", "U+E51C"], ["󾆫", "U+FE1AB"]],
  "👻": ["U+1F47B", "ghost", "1f47b", ["-", "-"], ["", "U+E4CB"], ["", "U+E11B"], ["󾆮", "U+FE1AE"]],
  "👼": ["U+1F47C", "baby angel", "1f47c", ["-", "-"], ["", "U+E5BF"], ["", "U+E04E"], ["󾆯", "U+FE1AF"]],
  "👽": ["U+1F47D", "extraterrestrial alien", "1f47d", ["-", "-"], ["", "U+E50E"], ["", "U+E10C"], ["󾆰", "U+FE1B0"]],
  "👾": ["U+1F47E", "alien monster", "1f47e", ["-", "-"], ["", "U+E4EC"], ["", "U+E12B"], ["󾆱", "U+FE1B1"]],
  "👿": ["U+1F47F", "imp", "1f47f", ["-", "-"], ["", "U+E4EF"], ["", "U+E11A"], ["󾆲", "U+FE1B2"]],
  "💀": ["U+1F480", "skull", "1f480", ["-", "-"], ["", "U+E4F8"], ["", "U+E11C"], ["󾆳", "U+FE1B3"]],
  "💁": ["U+1F481", "information desk person", "1f481", ["-", "-"], ["-", "-"], ["", "U+E253"], ["󾆴", "U+FE1B4"]],
  "💂": ["U+1F482", "guardsman", "1f482", ["-", "-"], ["-", "-"], ["", "U+E51E"], ["󾆵", "U+FE1B5"]],
  "💃": ["U+1F483", "dancer", "1f483", ["-", "-"], ["", "U+EB1C"], ["", "U+E51F"], ["󾆶", "U+FE1B6"]],
  "🐍": ["U+1F40D", "snake", "1f40d", ["-", "-"], ["", "U+EB22"], ["", "U+E52D"], ["󾇓", "U+FE1D3"]],
  "🐎": ["U+1F40E", "horse", "1f40e", ["", "U+E754"], ["", "U+E4D8"], ["", "U+E134"], ["󾟜", "U+FE7DC"]],
  "🐔": ["U+1F414", "chicken", "1f414", ["-", "-"], ["", "U+EB23"], ["", "U+E52E"], ["󾇔", "U+FE1D4"]],
  "🐗": ["U+1F417", "boar", "1f417", ["-", "-"], ["", "U+EB24"], ["", "U+E52F"], ["󾇕", "U+FE1D5"]],
  "🐫": ["U+1F42B", "bactrian camel", "1f42b", ["-", "-"], ["", "U+EB25"], ["", "U+E530"], ["󾇖", "U+FE1D6"]],
  "🐘": ["U+1F418", "elephant", "1f418", ["-", "-"], ["", "U+EB1F"], ["", "U+E526"], ["󾇌", "U+FE1CC"]],
  "🐨": ["U+1F428", "koala", "1f428", ["-", "-"], ["", "U+EB20"], ["", "U+E527"], ["󾇍", "U+FE1CD"]],
  "🐒": ["U+1F412", "monkey", "1f412", ["-", "-"], ["", "U+E4D9"], ["", "U+E528"], ["󾇎", "U+FE1CE"]],
  "🐑": ["U+1F411", "sheep", "1f411", ["-", "-"], ["", "U+E48F"], ["", "U+E529"], ["󾇏", "U+FE1CF"]],
  "🐙": ["U+1F419", "octopus", "1f419", ["-", "-"], ["", "U+E5C7"], ["", "U+E10A"], ["󾇅", "U+FE1C5"]],
  "🐚": ["U+1F41A", "spiral shell", "1f41a", ["-", "-"], ["", "U+EAEC"], ["", "U+E441"], ["󾇆", "U+FE1C6"]],
  "🐛": ["U+1F41B", "bug", "1f41b", ["-", "-"], ["", "U+EB1E"], ["", "U+E525"], ["󾇋", "U+FE1CB"]],
  "🐠": ["U+1F420", "tropical fish", "1f420", ["", "U+E751"], ["", "U+EB1D"], ["", "U+E522"], ["󾇉", "U+FE1C9"]],
  "🐤": ["U+1F424", "baby chick", "1f424", ["", "U+E74F"], ["", "U+E4E0"], ["", "U+E523"], ["󾆺", "U+FE1BA"]],
  "🐦": ["U+1F426", "bird", "1f426", ["", "U+E74F"], ["", "U+E4E0"], ["", "U+E521"], ["󾇈", "U+FE1C8"]],
  "🐧": ["U+1F427", "penguin", "1f427", ["", "U+E750"], ["", "U+E4DC"], ["", "U+E055"], ["󾆼", "U+FE1BC"]],
  "🐟": ["U+1F41F", "fish", "1f41f", ["", "U+E751"], ["", "U+E49A"], ["", "U+E019"], ["󾆽", "U+FE1BD"]],
  "🐬": ["U+1F42C", "dolphin", "1f42c", ["-", "-"], ["", "U+EB1B"], ["", "U+E520"], ["󾇇", "U+FE1C7"]],
  "🐭": ["U+1F42D", "mouse face", "1f42d", ["-", "-"], ["", "U+E5C2"], ["", "U+E053"], ["󾇂", "U+FE1C2"]],
  "🐯": ["U+1F42F", "tiger face", "1f42f", ["-", "-"], ["", "U+E5C0"], ["", "U+E050"], ["󾇀", "U+FE1C0"]],
  "🐱": ["U+1F431", "cat face", "1f431", ["", "U+E6A2"], ["", "U+E4DB"], ["", "U+E04F"], ["󾆸", "U+FE1B8"]],
  "🐳": ["U+1F433", "spouting whale", "1f433", ["-", "-"], ["", "U+E470"], ["", "U+E054"], ["󾇃", "U+FE1C3"]],
  "🐴": ["U+1F434", "horse face", "1f434", ["", "U+E754"], ["", "U+E4D8"], ["", "U+E01A"], ["󾆾", "U+FE1BE"]],
  "🐵": ["U+1F435", "monkey face", "1f435", ["-", "-"], ["", "U+E4D9"], ["", "U+E109"], ["󾇄", "U+FE1C4"]],
  "🐶": ["U+1F436", "dog face", "1f436", ["", "U+E6A1"], ["", "U+E4E1"], ["", "U+E052"], ["󾆷", "U+FE1B7"]],
  "🐷": ["U+1F437", "pig face", "1f437", ["", "U+E755"], ["", "U+E4DE"], ["", "U+E10B"], ["󾆿", "U+FE1BF"]],
  "🐻": ["U+1F43B", "bear face", "1f43b", ["-", "-"], ["", "U+E5C1"], ["", "U+E051"], ["󾇁", "U+FE1C1"]],
  "🐹": ["U+1F439", "hamster face", "1f439", ["-", "-"], ["-", "-"], ["", "U+E524"], ["󾇊", "U+FE1CA"]],
  "🐺": ["U+1F43A", "wolf face", "1f43a", ["", "U+E6A1"], ["", "U+E4E1"], ["", "U+E52A"], ["󾇐", "U+FE1D0"]],
  "🐮": ["U+1F42E", "cow face", "1f42e", ["-", "-"], ["", "U+EB21"], ["", "U+E52B"], ["󾇑", "U+FE1D1"]],
  "🐰": ["U+1F430", "rabbit face", "1f430", ["-", "-"], ["", "U+E4D7"], ["", "U+E52C"], ["󾇒", "U+FE1D2"]],
  "🐸": ["U+1F438", "frog face", "1f438", ["-", "-"], ["", "U+E4DA"], ["", "U+E531"], ["󾇗", "U+FE1D7"]],
  "😠": ["U+1F620", "angry face", "1f620", ["", "U+E6F1"], ["", "U+E472"], ["", "U+E059"], ["󾌠", "U+FE320"]]
};

if(!String.prototype.trim){
	String.prototype.trim=function()
	{
		return this.replace(/(^\s*)|(\s*$)/g,"");
	}
	
	String.prototype.ltrim=function()
	{
		return this.replace(/(^\s*)/g,"");
	}

	String.prototype.rtrim=function()
	{
		return this.replace(/(\s*$)/g,"");
	}
}
if (!Object.keys) {
  Object.keys = (function() {
    'use strict';
    var hasOwnProperty = Object.prototype.hasOwnProperty,
        hasDontEnumBug = !({ toString: null }).propertyIsEnumerable('toString'),
        dontEnums = [
          'toString',
          'toLocaleString',
          'valueOf',
          'hasOwnProperty',
          'isPrototypeOf',
          'propertyIsEnumerable',
          'constructor'
        ],
        dontEnumsLength = dontEnums.length;

    return function(obj) {
      if (typeof obj !== 'object' && (typeof obj !== 'function' || obj === null)) {
        throw new TypeError('Object.keys called on non-object');
      }

      var result = [], prop, i;

      for (prop in obj) {
        if (hasOwnProperty.call(obj, prop)) {
          result.push(prop);
        }
      }

      if (hasDontEnumBug) {
        for (i = 0; i < dontEnumsLength; i++) {
          if (hasOwnProperty.call(obj, dontEnums[i])) {
            result.push(dontEnums[i]);
          }
        }
      }
      return result;
    };
  }());
}

/**
 * Create map keys rexgep, keys sort by key's length desc.
 *
 * @param {Object} map
 * @return {RegExp}
 */
function _createRegexp(map) {
  var keys = Object.keys(map);
  keys.sort(function (a, b) {
    return b.length - a.length;
  });
  return new RegExp('(' + keys.join('|') + ')', 'g');
}

var EMOJI_RE = null;
/**
 * Convert unified code to HTML.
 *
 * @param {String} text
 * @return {String} html with emoji classname.
 */
function unifiedToHTML(text) {
  return text.replace(jEmoji.EMOJI_RE(), function (_, m) {
    var em = EMOJI_MAP[m];
    var text=em[5][1].replace(/U\+/g,"\\u");
    return '<em class="emoji emoji' + em[2] + '" onclick="emojitohtml(\''+text+'\')">'+em[5][0]+'</em>';
  });
}
jEmoji.unifiedToHTML = unifiedToHTML;

var EMOJI_DOCOMO_MAP = {};
var EMOJI_KDDI_MAP = {};
var EMOJI_SOFTBANK_MAP = {};
var EMOJI_GOOGLE_MAP = {};
var _maps = [EMOJI_DOCOMO_MAP, EMOJI_KDDI_MAP, EMOJI_SOFTBANK_MAP, EMOJI_GOOGLE_MAP];
for (var k in EMOJI_MAP) {
  var em = EMOJI_MAP[k];
  for (var i = 0; i < _maps.length; i++) {
    var index = i + 3;
    var code = em[index][0];
    var map = _maps[i];
    if (code === '-' || map[code]) { // use first code
      continue;
    }
    map[code] = k;
  }
}

var EMOJI_DOCOMO_RE = null;
/**
 * Convert DoCoMo code to Unified code.
 *
 * @param {String} text
 * @return {String}
 */
function docomoToUnified(text) {
  return text.replace(jEmoji.EMOJI_DOCOMO_RE(), function (_, m) {
    return EMOJI_DOCOMO_MAP[m];
  });
}
jEmoji.docomoToUnified = docomoToUnified;

var EMOJI_KDDI_RE = null;
/**
 * Convert KDDI code to Unified code.
 *
 * @param {String} text
 * @return {String}
 */
function kddiToUnified(text) {
  return text.replace(jEmoji.EMOJI_KDDI_RE(), function (_, m) {
    return EMOJI_KDDI_MAP[m];
  });
}
jEmoji.kddiToUnified = kddiToUnified;

var EMOJI_SOFTBANK_RE = null;
/**
 * Convert SoftBank code to Unified code.
 *
 * @param {String} text
 * @return {String}
 */
function softbankToUnified(text) {
  return text.replace(jEmoji.EMOJI_SOFTBANK_RE(), function (_, m) {
    return EMOJI_SOFTBANK_MAP[m];
  });
}
jEmoji.softbankToUnified = softbankToUnified;

var EMOJI_GOOGLE_RE = null;
/**
 * Convert Google code to Unified code.
 *
 * @param {String} text
 * @return {String}
 */
function googleToUnified(text) {
  return text.replace(jEmoji.EMOJI_GOOGLE_RE(), function (_, m) {
    return EMOJI_GOOGLE_MAP[m];
  });
}
jEmoji.googleToUnified = googleToUnified;

return jEmoji;

});
