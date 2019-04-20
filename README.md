# string formatter

## can be used like:
```new StringFormatter("{}{}").format("123", "abc").toString(); //result is 123abc```


```new StringFormatter("{}{}").format("123").format("abc").toString(); //result is 123abc```


```new StringFormatter("{}{}").format("123").toString(); //result is 123{}```

