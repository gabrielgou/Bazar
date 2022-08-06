const criaIterator = (iterable) => {
    let idx = -1;
    return {
        next:() =>{
            idx++
            return idx < iterable.length ? true:false
        },
        getString: (string)=>{
            for([name,value] of Object.entries(iterable[idx]))
            {
                if(name==string)
                {
                    return value
                }
            }
        }
        }
    };