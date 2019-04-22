# string formatter

## can be used like:
### formatString(CharSequence... replacements)
```new StringFormatter("{}{}").formatString("123", "abc").toString(); //result is 123abc```


```new StringFormatter("{}{}").formatString("123").formatString("abc").toString(); //result is 123abc```


```new StringFormatter("{}{}").formatString("123").toString(); //result is 123{}```


### format(String placeholder, replacement)
```new StringFormatter("{name} {age}").format("name", "Levy").format("age", 40).toString(); //result is Levy 40```


### format(Map<String, Object> replacementMap)
```new StringFormatter("{name} {age}").format(map).toString(); //result is Levy 40```


### format(T replacement)
```new StringFormatter("{name} {age}").format(student).toString(); //result is Levy 40```

